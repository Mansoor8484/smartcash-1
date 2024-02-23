package rebelalliance.smartcash.http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import rebelalliance.smartcash.ledger.container.Account;

import java.util.List;

public class AccountsHandler extends SmartCashHttpHandler implements HttpHandler {
    public AccountsHandler() {
        super("/accounts");
    }

    @Override
    public void handle(HttpExchange exchange) {
        List<Account> accounts = this.ledger.getAccounts();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        StringBuilder response = new StringBuilder();
        response.append("{accounts:[");
        for(Account account : accounts) {
            response.append("\"").append(account.getName()).append("\",");
        }
        response.append("]}");
        String responseString = response.toString().replace(",]", "]");
        this.send(responseString, exchange);
    }
}
