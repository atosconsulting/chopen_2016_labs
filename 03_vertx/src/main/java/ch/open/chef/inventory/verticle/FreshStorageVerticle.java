package ch.open.chef.inventory.verticle;

import ch.open.chef.inventory.domain.StockItem;
import ch.open.chef.inventory.service.InventoryRepository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FreshStorageVerticle extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(FreshStorageVerticle.class);

    public static final String COLLECTION = "fresh";
    public static final String CHECK = "fresh-check";
    public static final String RESERVE = "fresh-reserve";

    @Override
    public void start(Future<Void> fut) throws Exception {
        final InventoryRepository inventoryRepository = new InventoryRepository(MongoClient.createShared(vertx, config()));
        vertx.eventBus().consumer(CHECK, event -> {
            final List<StockItem> stockItems = StockItem.fromJsonArray((JsonArray) event.body());
            final List<Future> queryResults = inventoryRepository.findInsufficientIngredients(COLLECTION, stockItems);
            CompositeFuture.all(queryResults).setHandler(result -> {
                final JsonArray missingInventory = new JsonArray(result.result().<StockItem>list().stream().filter(Objects::nonNull).map(StockItem::toJson).collect(Collectors.toList()));
                LOG.info("Missing in the fresh room: " + missingInventory);
                event.reply(missingInventory);
            });
        });

        vertx.eventBus().consumer(RESERVE, event -> {
            final List<StockItem> stockItems = StockItem.fromJsonArray((JsonArray) event.body());
            final List<Future> queryResults = inventoryRepository.updateStockItems(COLLECTION, stockItems);
            CompositeFuture.all(queryResults).setHandler(result -> {
                LOG.info(RESERVE + " : " + stockItems);
                event.reply(true);
            });
        });
    }
}
