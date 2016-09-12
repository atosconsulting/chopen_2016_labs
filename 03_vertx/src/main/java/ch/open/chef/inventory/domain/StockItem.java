package ch.open.chef.inventory.domain;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

public class StockItem {

    private final String stockId;

    private final Double amount;

    public StockItem() {
        this("", 0.0d);
    }

    public StockItem(String stockId, Double amount) {
        this.stockId = stockId;
        this.amount = amount;
    }

    public String getStockId() {
        return stockId;
    }

    public Double getAmount() {
        return amount;
    }

    public JsonObject toJson() {
        return new JsonObject(Json.encode(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockItem stockItem = (StockItem) o;

        return getStockId().equals(stockItem.getStockId());

    }

    @Override
    public int hashCode() {
        return getStockId().hashCode();
    }

    @Override
    public String toString() {
        return "StockItem{" +
                "stockId='" + stockId + '\'' +
                ", amount=" + amount +
                '}';
    }

    public static StockItem fromJson(Object json) {
        return Json.decodeValue(json.toString(), StockItem.class);
    }

    public static List<StockItem> fromJsonArray(JsonArray array) {
        return array.stream().map(StockItem::fromJson).collect(Collectors.toList());
    }

}
