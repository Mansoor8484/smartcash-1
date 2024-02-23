package rebelalliance.smartcash.http.handler;

import com.sun.net.httpserver.HttpExchange;
import rebelalliance.smartcash.ledger.Ledger;

import java.io.OutputStream;

public class SmartCashHttpHandler {
    String route;
    Ledger ledger;

    public SmartCashHttpHandler(String route) {
        this.route = route;
    }

    public void send(String response, HttpExchange exchange) {
        try {
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String response, int status, HttpExchange exchange) {
        try {
            exchange.sendResponseHeaders(status, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
    }

    public String getRoute() {
        return this.route;
    }
}
