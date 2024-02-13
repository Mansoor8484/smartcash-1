package rebelalliance.smartcash.http;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import rebelalliance.smartcash.http.handler.SmartCashHttpHandler;
import rebelalliance.smartcash.http.handler.AccountsHandler;
import rebelalliance.smartcash.ledger.Ledger;

import java.net.InetSocketAddress;

public class SmartCashHttpServer {
    Ledger ledger;

    private final SmartCashHttpHandler[] HANDLERS = {
            new AccountsHandler()
    };

    public SmartCashHttpServer(Ledger ledger) {
        this.ledger = ledger;
    }

    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(2891), 0);
            for(SmartCashHttpHandler handler : HANDLERS) {
                handler.setLedger(this.ledger);
                server.createContext(handler.getRoute(), (HttpHandler) handler);
            }
            server.setExecutor(null);
            server.start();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
