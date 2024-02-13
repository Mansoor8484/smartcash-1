package rebelalliance.smartcash.http.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import rebelalliance.smartcash.ledger.container.Category;

import java.util.List;

public class CategoriesHandler extends SmartCashHttpHandler implements HttpHandler {
    public CategoriesHandler() {
        super("/categories");
    }

    @Override
    public void handle(HttpExchange exchange) {
        List<Category> accounts = this.ledger.getCategories();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        StringBuilder response = new StringBuilder();
        response.append("{categories:[");
        for(Category category : accounts) {
            response.append("\"").append(category.getName()).append("\",");
        }
        response.append("]}");
        String responseString = response.toString().replace(",]", "]");
        this.send(responseString, exchange);
    }
}
