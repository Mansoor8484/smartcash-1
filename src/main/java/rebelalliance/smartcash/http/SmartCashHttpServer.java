package rebelalliance.smartcash.http;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import rebelalliance.smartcash.http.handler.SmartCashHttpHandler;
import rebelalliance.smartcash.http.handler.TestHandler;

import java.net.InetSocketAddress;

public class SmartCashHttpServer {
    private final SmartCashHttpHandler[] HANDLERS = {
            new TestHandler()
    };

    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(2891), 0);
            for(SmartCashHttpHandler handler : HANDLERS) {
                server.createContext(handler.getRoute(), (HttpHandler) handler);
            }
            server.setExecutor(null);
            server.start();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
