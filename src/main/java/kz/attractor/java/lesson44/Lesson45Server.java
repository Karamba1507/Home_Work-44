package kz.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import kz.attractor.java.server.ContentType;
import kz.attractor.java.server.Cookie;
import kz.attractor.java.server.ResponseCodes;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static kz.attractor.java.server.Utils.parseUrlEncoded;

public class Lesson45Server extends Lesson44Server {

    public static Users users = new Users();

    public Lesson45Server(String host, int port) throws IOException {
        super(host, port);

        // HomeWork#45
        getHandler("/login", this::loginGet);
        getHandler("/logout", this::logout);
        postHandler("/login", this::userHandler);
        postHandler("/registration", this::newUser);

        users.fillUserList();


    }

//    private void registrationPost(HttpExchange exchange) {
//        String cType = getContentType(exchange);
//        String raw = getBody(exchange);
//        Map<String, String> parsed = parseUrlEncoded(raw, "&");
//
//        String data = String.format("<%s>", raw, cType, parsed);
//
//        try {
//            sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private void registrationPost(HttpExchange exchange) {
//        String cType = getContentType(exchange);
//        String raw = getBody(exchange);
//        Map<String, String> parsed = parseUrlEncoded(raw, "&");
//
//        String data = String.format("<%s>", raw, cType, parsed);
//
//        try {
//            sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private void registrationHandler(HttpExchange exchange) {
//
//        String raw = getBody(exchange);
//        // преобразуем данные в формате form-urlencoded,
//        // обратно в читаемый вид.
//        Map<String, String> parsed = parseUrlEncoded(raw, "&");
//        // отправим данные обратно пользователю,
//        // что бы показать, что мы обработали запрос
//        System.out.println(parsed);
//
//        User authorizationUser = null;
//
//        for (User user : users.getUsers()) {
//
//            if (parsed.get("email").equals(user.getEmail())) {
//                authorizationUser = user;
//                break;
//            }
//        }
//
//        if (authorizationUser != null) {
//            Path path = makeFilePath("registration.html");
//            sendFile(exchange, path, ContentType.TEXT_HTML);
//        } else {
//
//
//            renderTemplate(exchange, "users.html", users);
//        }
//    }

    private void userHandler(HttpExchange exchange) {

        String raw = getBody(exchange);
        // преобразуем данные в формате form-urlencoded,
        // обратно в читаемый вид.
        Map<String, String> parsed = parseUrlEncoded(raw, "&");
        // отправим данные обратно пользователю,
        // что бы показать, что мы обработали запрос
        System.out.println(parsed);

        User authorizationUser = null;

        for (User user : users.getUsers()) {

            if (parsed.get("email").equals(user.getEmail())) {
                if (parsed.get("user-password").equals(user.getPassword())) {
                    authorizationUser = user;
                }
                break;
            }
        }

        if (authorizationUser == null) {
            Path path = makeFilePath("loginError.html");
            sendFile(exchange, path, ContentType.TEXT_HTML);
        } else {

            String cookieId = UUID.randomUUID().toString();

            for (User user : users.getUsers()) {
                if (user.getEmail().equals(parsed.get("email"))) {
                    user.setCookieId(cookieId);
                }
            }

            Cookie sessionCookie = Cookie.make("cookieId", cookieId);
            Cookie emailCookie = Cookie.make("email", parsed.get("email"));

            exchange.getResponseHeaders().add("Set-Cookie", sessionCookie.toString());
            exchange.getResponseHeaders().add("Set-Cookie", emailCookie.toString());

            renderTemplate(exchange, "users.html", users);
        }
    }

    private void newUser(HttpExchange exchange) {
        String raw = getBody(exchange);
        Map<String, String> parsed = parseUrlEncoded(raw, "&");
        System.out.println(parsed);

        for (User user : users.getUsers()) {
            if (user.getEmail().equals(parsed.get("email"))) {
                Path path = makeFilePath("registrationError.html");
                sendFile(exchange, path, ContentType.TEXT_HTML);
                return;
            }
        }

        String uuid = UUID.randomUUID().toString();

        User user = new User(null, null, null, parsed.get("email"), parsed.get("user-password"));

        users.getUsers().add(user);

        renderTemplate(exchange, "newUser.html", user);

    }

//    private void bookList(HttpExchange exchange) {
//
//        String cookies = getCookies(exchange);
//
//        String raw = getBody(exchange);
//        Map<String, String> parsed = parseUrlEncoded(raw, "&");
//        System.out.println(parsed);
//
//        for (User user : users.getUsers()) {
//            if (user.getEmail().equals(parsed.get("email"))) {
//                Path path = makeFilePath("registrationError.html");
//                sendFile(exchange, path, ContentType.TEXT_HTML);
//                return;
//            }
//        }
//
//        String uuid = UUID.randomUUID().toString();
//
//        User user = new User(null, null, null, parsed.get("email"), parsed.get("user-password"));
//
//        users.getUsers().add(user);
//
//        renderTemplate(exchange, "newUser.html", user);
//
//    }

//    private void loginGetError(HttpExchange exchange) {
//        Path path = makeFilePath("loginFail.html");
//        sendFile(exchange, path, ContentType.TEXT_HTML);
//    }

//    private void loginPost(HttpExchange exchange) {
//        String cType = getContentType(exchange);
//        String raw = getBody(exchange);
//
//        Map<String, String> parsed = parseUrlEncoded(raw, "&");
//
//        String data = String.format("<p>Необработанные данные: <b>%s</b></p>" +
//                "<p>Content-Type: <b>%s</b></p>" +
//                "<p>После обработки: <b>%s</b></p>", raw, cType, parsed);
//
//        try {
//            sendByteData(exchange, ResponseCodes.OK, ContentType.TEXT_HTML, data.getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void loginGet(HttpExchange exchange) {
        Path path = makeFilePath("login.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

    private void logout(HttpExchange exchange) {

        for (User user : users.getUsers()) {
            user.setCookieId(null);
        }

//        Cookie sessionCookie = Cookie.make("cookieId", cookieId);
//        Cookie emailCookie = Cookie.make("email", parsed.get("email"));

        exchange.getResponseHeaders().clear();


        Path path = makeFilePath("login.html");
        sendFile(exchange, path, ContentType.TEXT_HTML);
    }

//    String getCookies(HttpExchange exchange) {
//        return exchange.getRequestHeaders()
//                .getOrDefault("Cookie", List.of(""))
//                .get(0);
//    }

}
