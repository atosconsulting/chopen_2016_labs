package ch.open.chef.inventory.verticle;

import ch.open.chef.inventory.domain.StockItem;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryResource extends AbstractVerticle {


    @Override
    public void start(Future<Void> future) throws Exception {
        final Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

        router.post("/checkout").handler(rc -> {
            final JsonArray stockItems = rc.getBodyAsJsonArray();

            vertx.eventBus().<JsonArray>send(FreshStorageVerticle.CHECK, stockItems, notAvailableFresh -> {
                if (notAvailableFresh.result().body().isEmpty())  {
                    vertx.eventBus().send(FreshStorageVerticle.RESERVE, stockItems);
                } else {
                    vertx.eventBus().<JsonArray>send(FridgeVerticle.CHECK, notAvailableFresh.result().body(), notAvailableInTheFridge -> {
                        if (notAvailableInTheFridge.result().body().isEmpty()) {
                            final JsonArray toOrderFromFridge = notAvailableFresh.result().body();
                            vertx.eventBus().send(FridgeVerticle.RESERVE, toOrderFromFridge);
                            vertx.eventBus().send(FreshStorageVerticle.RESERVE, remaining(stockItems, toOrderFromFridge));
                            rc.response().setStatusCode(200).end();
                        } else {
                            rc.response().setStatusCode(404).end();
                        }

                    });
                }
            });
        });

        vertx.createHttpServer()
            .requestHandler(router::accept)
            .listen(
                    config().getInteger("http.port", 8282),
                    result -> {
                        if (result.succeeded()) {
                            future.complete();
                        } else {
                            future.fail(result.cause());
                        }
                    }
            );
    }

    private JsonArray remaining(JsonArray stockItems, JsonArray toOrderFromFridge) {
        final List<StockItem> remainingItems = StockItem.fromJsonArray(stockItems);
        remainingItems.removeAll(StockItem.fromJsonArray(toOrderFromFridge));
        return new JsonArray(remainingItems.stream().map(Json::encode).collect(Collectors.toList()));

    }
}
