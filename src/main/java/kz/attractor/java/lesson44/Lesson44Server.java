package kz.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
//import javafx.util.Pair;
import jdk.jshell.execution.Util;
import kz.attractor.java.server.BasicServer;
import kz.attractor.java.server.ContentType;
import kz.attractor.java.server.ResponseCodes;
import kz.attractor.java.server.Utils;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static kz.attractor.java.lesson44.Lesson45Server.users;

public class Lesson44Server extends BasicServer {
    private final static Configuration freemarker = initFreeMarker();

    Books books = new Books();
    Employees employees = new Employees();

    public Lesson44Server(String host, int port) throws IOException {
        super(host, port);
        loginGet("/sample", this::freemarkerSampleHandler);

        loginGet("/test", this::testHandler);

        loginGet("/books", this::booksHandler);

        loginGet("/getBook", this::getBookHandler);

        loginGet("/returnBook", this::returnBook);


//        loginGet("/emp", this::emtHandler);
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

    private void freemarkerSampleHandler(HttpExchange exchange) {
        renderTemplate(exchange, "sample.html", getSampleDataModel());
    }

    private void testHandler(HttpExchange exchange) {
        renderTemplate(exchange, "test.html", new TestDataModel());
    }

    private void booksHandler(HttpExchange exchange) {

        User autorisedUser = userValidate(exchange);

        if (autorisedUser != null) {

            UserBooks books = new UserBooks();

            for (Book book : this.books.getBooks()) {
                boolean isHandle = book.getEmplyeEmail() != null && book.getEmplyeEmail().equals(autorisedUser.getEmail());
                UserBook userBook = new UserBook(book.getId(), book.getAuthor(), book.getTitle(), book.getDescription(), book.getEmplyeEmail(), isHandle);
                books.getBooks().add(userBook);
            }

            renderTemplate(exchange, "books.html", books);
        } else {
            Path path = makeFilePath("loginError.html");
            sendFile(exchange, path, ContentType.TEXT_HTML);
        }
    }

    private User userValidate(HttpExchange exchange) {

        String cookieId = getCookieFromUser(exchange, "cookieId");
        String userEmail = getCookieFromUser(exchange, "email");

        User autorisedUser = null;

        for (User user : users.getUsers()) {
            if (user.getEmail().equals(userEmail)) {
                if (user.getCookieId() != null && user.getCookieId().equals(cookieId)) {
                    autorisedUser = user;
                }
                break;
            }
        }

        return autorisedUser;
    }

    private String getCookieFromUser(HttpExchange exchange, String key) {

        String cookies = getCookies(exchange);

        String decode = URLDecoder.decode(cookies, UTF_8);

        String result = "";

        String[] split = decode.split(";");
        for (String s : split) {
            if (s.contains(key)) {
                result = s.split("=")[1].trim();
            }
        }

        return result;
    }

    private User findAutorisedUser(HttpExchange exchange) {
        String cookies = getCookies(exchange);

        return null;
    }

    private void emtHandler(HttpExchange exchange) {

        renderTemplate(exchange, "emp.html", employees);
    }

    private void getBookHandler(HttpExchange exchange) {

        User autorizedUser = userValidate(exchange);

        if (autorizedUser == null) {
            Path path = makeFilePath("loginError.html");
            sendFile(exchange, path, ContentType.TEXT_HTML);
            return;
        }

        if (autorizedUser.getBookIds().size() > 1) {
            Path path = makeFilePath("failedGetBook.html");
            sendFile(exchange, path, ContentType.TEXT_HTML);
            return;
        }

        String params = exchange.getRequestURI().getQuery();

        // Получаем id из запроса
        String id = params.split("=")[1];

        Book book = null;

        for (Book book1 : books.getBooks()) {
            if (book1.getId() == Integer.parseInt(id)) {
                book = book1;
                break;
            }
        }

        String text;

        assert book != null;
        if (book.getEmplyeEmail() == null ) {

            text = "Tou successful get the book " + book.getTitle();

            book.setEmplyeEmail(autorizedUser.getEmail());
            book.getUsageHistory().add(autorizedUser.getEmail());

            for (User user : users.getUsers()) {
                if (user.getEmail().equals(autorizedUser.getEmail())) {
                    user.getBookIds().add(book.getId());
                    break;
                }
            }

        } else {
            text = "The book is not available";
        }

        renderTemplate(exchange, "getBooks.html", new Message(text));
    }

    private void returnBook(HttpExchange exchange) {

        User autorizedUser = userValidate(exchange);

        if (autorizedUser == null) {
            Path path = makeFilePath("loginError.html");
            sendFile(exchange, path, ContentType.TEXT_HTML);
            return;
        }

        String params = exchange.getRequestURI().getQuery();

        // Получаем id из запроса
        String bookId = params.split("=")[1];

        Book book = null;

        for (Book book1 : books.getBooks()) {
            if (book1.getId() == Integer.parseInt(bookId)) {
                book = book1;
                book1.setEmplyeEmail(null);
                break;
            }
        }

        for (User user : users.getUsers()) {

            if (user.getEmail().equals(autorizedUser.getEmail())) {
                user.getBookIds().remove((Object) Integer.parseInt(bookId));
                break;
            }
        }

        String text = "You return the book " + book.getTitle();

        renderTemplate(exchange, "returnBook.html", new Message(text));
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

    private SampleDataModel getSampleDataModel() {
        // возвращаем экземпляр тестовой модели-данных
        // которую freemarker будет использовать для наполнения шаблона
        return new SampleDataModel();
    }
}

