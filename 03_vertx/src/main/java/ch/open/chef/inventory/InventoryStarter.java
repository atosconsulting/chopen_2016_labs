package ch.open.chef.inventory;

import ch.open.chef.inventory.verticle.FreshStorageVerticle;
import ch.open.chef.inventory.verticle.FridgeVerticle;
import ch.open.chef.inventory.verticle.InventoryResource;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.MongoClient;

import java.util.Scanner;

public class InventoryStarter extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryStarter.class);

    @Override
    public void start(Future<Void> fut) throws Exception {
        LOG.info("Starting Inventory ...");

        config().put("db_name", "inventory");
        config().put("connection_string", "mongodb://localhost:27017");
        seedMongo("fresh",
                (nothing) -> seedMongo("fridge", (deployment) -> {
                    vertx.deployVerticle(new FridgeVerticle());
                    vertx.deployVerticle(new FreshStorageVerticle());
                    vertx.deployVerticle(new InventoryResource());
                    LOG.info("Started");
                }, fut), fut);

    }


    private void seedMongo(String collection, Handler<AsyncResult<Void>> next, Future<Void> fut) {
        final MongoClient mongo = MongoClient.createShared(vertx, config());
        mongo.count(collection, new JsonObject(), count -> {
            if (count.succeeded()) {
                if (count.result() == 0) {
                    try (final Scanner scanner = new Scanner(Thread.currentThread().getContextClassLoader().getResourceAsStream(collection + ".json")).useDelimiter("\\Z")) {
                        final JsonArray jsonArray = new JsonArray(scanner.next());
                        jsonArray.forEach(json -> {
                            mongo.insert(collection, new JsonObject(json.toString()), insert -> {
                                if (!insert.succeeded()) {
                                    fut.fail(insert.cause());
                                }
                            });
                        });
                    }
                }

                if (!fut.failed()) {
                    next.handle(Future.succeededFuture());
                }

            } else {
                // report the error
                fut.fail(count.cause());
            }
        });
    }
}
