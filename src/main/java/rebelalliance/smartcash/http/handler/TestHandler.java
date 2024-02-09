package rebelalliance.smartcash.http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class TestHandler extends SmartCashHttpHandler implements HttpHandler {
    public TestHandler() {
        super("/test");
    }

    @Override
    public void handle(HttpExchange exchange) {
        this.send("Test.", exchange);
    }
}
