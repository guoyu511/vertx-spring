package io.vertx.ext.spring;

import java.util.Arrays;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;

/**
 * Created by guoyu on 16/6/23.
 */
public class MultiVerticleServer {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        CompositeFuture.all(
            Arrays.asList(
                deploy(vertx),
                deploy(vertx),
                deploy(vertx),
                deploy(vertx))
        ).setHandler((res) -> {
            if (res.failed()) {
                res.cause().printStackTrace();
            }
        });
    }

    private static Future<String> deploy(Vertx vertx) {
        Future<String> future = Future.future();
        VertxSpring.deploy(vertx,
            "test-context.xml",
            new HttpServerOptions()
                .setPort(8080),
            future.completer());
        return future;
    }

}
