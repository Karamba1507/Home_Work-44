package exam.java.exam6;

import com.sun.net.httpserver.HttpExchange;
import exam.java.exam6.models.Message;
import exam.java.exam6.models.Month;
import exam.java.server.RouteHandler;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import exam.java.server.BasicServer;
import exam.java.server.ContentType;
import exam.java.server.ResponseCodes;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

import static exam.java.server.Utils.parseUrlEncoded;

public class Lesson44Server extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();

    private Month month = new Month();

    public Lesson44Server(String host, int port) throws IOException {
        super(host, port);

        getHandler("/calendar", this::getCalendar);
        getHandler("/addTask", this::addTask);
        postHandler("/createTask", this::createTask);
    }

    private void getCalendar(HttpExchange exchange) {
        renderTemplate(exchange, "calendar.html", month);
    }

    private void addTask(HttpExchange exchange) {

        String params = exchange.getRequestURI().getQuery();

        String date = params.split("=")[1];

        renderTemplate(exchange, "createTask.html", new Message(date));
    }

    private void createTask(HttpExchange exchange) {

        String raw = getBody(exchange);
        Map<String, String> parsed = parseUrlEncoded(raw, "&");
        System.out.println(parsed);

        String params = exchange.getRequestURI().getQuery();

        String date = params.split("=")[1];

        renderTemplate(exchange, "createTask.html", new Message(date));
    }

    private static Configuration initFreeMarker() {
        try {
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
            // путь к каталогу в котором у нас хранятся шаблоны
            // это может быть совершенно другой путь, чем тот, откуда сервер берёт файлы
            // которые отправляет пользователю
            cfg.setDirectoryForTemplateLoading(new File("data"));

            // прочие стандартные настройки о них читать тут
            // https://freemarker.apache.org/docs/pgui_quickstart_createconfiguration.html
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
            return cfg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void renderTemplate(HttpExchange exchange, String templateFile, Object dataModel) {
        try {
            // загружаем шаблон из файла по имени.
            // шаблон должен находится по пути, указанном в конфигурации
            Template temp = freemarker.getTemplate(templateFile);

            // freemarker записывает преобразованный шаблон в объект класса writer
            // а наш сервер отправляет клиенту массивы байт
            // по этому нам надо сделать "мост" между этими двумя системами

            // создаём поток который сохраняет всё, что в него будет записано в байтовый массив
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            // создаём объект, который умеет писать в поток и который подходит для freemarker
            try (OutputStreamWriter writer = new OutputStreamWriter(stream)) {

                // обрабатываем шаблон заполняя его данными из модели
                // и записываем результат в объект "записи"
                temp.process(dataModel, writer);
                writer.flush();

                // получаем байтовый поток
                var data = stream.toByteArray();

                // отправляем результат клиенту
                sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data);
            }
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    protected final void getHandler(String route, RouteHandler handler) {
        getRoutes().put("GET " + route, handler);
    }

    protected final void postHandler(String route, RouteHandler handler) {
        getRoutes().put("POST " + route, handler);
    }

    protected final String getBody(HttpExchange exchange) {
        InputStream input = exchange.getRequestBody();
        InputStreamReader isr = new InputStreamReader(input, StandardCharsets.UTF_8);

        try (BufferedReader reader = new BufferedReader(isr)) {
            return reader.lines().collect(Collectors.joining(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
