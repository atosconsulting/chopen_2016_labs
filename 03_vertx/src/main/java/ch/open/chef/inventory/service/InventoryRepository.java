package ch.open.chef.inventory.service;

import ch.open.chef.inventory.domain.StockItem;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mongo.MongoClient;

import java.util.ArrayList;
import java.util.List;

public class InventoryRepository {

    private final MongoClient mongo;

    public InventoryRepository(MongoClient mongo) {
        this.mongo = mongo;
    }

    public List<Future> findInsufficientIngredients(String collectionName, List<StockItem> stockItems) {

        final List<Future> queryResults = new ArrayList<>(stockItems.size());
        stockItems.forEach(stockItem -> {
            final Future<StockItem> future = io.vertx.core.Future.future();
            queryResults.add(future);
            mongo.find(collectionName, amountGte(stockItem), result -> {
                if (result.succeeded() && result.result().isEmpty()) {
                    future.complete(stockItem);
                } else {
                    future.complete(); // if we would fail here we wouldn't find all missing elements
                }
            });
        });
        return queryResults;
    }

    public List<Future> updateStockItems(String collectionName, List<StockItem> stockItems) {

        final List<Future> queryResults = new ArrayList<>(stockItems.size());
        stockItems.forEach(stockItem -> {
            final Future<StockItem> future = io.vertx.core.Future.future();
            queryResults.add(future);
            mongo.updateCollection(collectionName, extractStockId(stockItem), updateStockAmount(stockItem), result -> {
                if (result.succeeded()) {
                    future.complete(stockItem);
                } else {
                    future.complete(); // if we would fail here we wouldn't find all missing elements
                }
            });
        });
        return queryResults;
    }

    private JsonObject updateStockAmount(StockItem stockItem) {
        return new JsonObject().put("$inc", new JsonObject().put("amount", stockItem.getAmount() * -1));
    }

    private JsonObject extractStockId(StockItem stockItem) {
        return new JsonObject().put("stockId", stockItem.getStockId());
    }

    private JsonObject amountGte(StockItem stockItem) {
        return new JsonObject().put("stockId", stockItem.getStockId()).put("amount", new JsonObject().put("$gte", stockItem.getAmount()));
    }

}
