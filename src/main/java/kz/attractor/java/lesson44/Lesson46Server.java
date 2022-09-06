package kz.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;
import kz.attractor.java.server.Cookie;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Lesson46Server extends Lesson45Server {
    public Lesson46Server(String host, int port) throws IOException {
        super(host, port);

        loginGet("/cookie", this::cookieHandler);
    }

    private void cookieHandler(HttpExchange exchange) {
        Cookie sessionCookie = Cookie.make("userId", "123");
        exchange.getResponseHeaders().add("Set-Cookie", sessionCookie.toString());

        Map<String, Object> data = new HashMap<>();
        String name = "times";

        Cookie c1 = Cookie.make("user%Id", "456");
        setCookie(exchange, c1);

        Cookie c2 = Cookie.make("user-mail", "456");
        setCookie(exchange, c2);

        Cookie c3 = Cookie.make("restricted()<>@,;;\\\"/[]?={}", "restricted()<>@,;;\\\"/[]?={}");
        setCookie(exchange, c3);


        String cookieString = getCookies(exchange);


        Map<String, String> cookies = Cookie.parse(cookieString);
        String cookieValue = cookies.getOrDefault(name, "0");
        int times = Integer.parseInt(cookieValue) + 1;

        Cookie<Integer> c4 = new Cookie<>(name, times);
        setCookie(exchange, c4);

        data.put(name, times);
        data.put("cookies", cookies);


        renderTemplate(exchange, "cookie.html", data);
    }
}
