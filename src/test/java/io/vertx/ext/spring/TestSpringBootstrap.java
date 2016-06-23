package io.vertx.ext.spring;

import org.junit.Test;

import java.util.Arrays;

import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.test.core.AsyncTestBase;

public class TestSpringBootstrap extends AsyncTestBase {

    private Vertx vertx = Vertx.vertx();

    private HttpClient client;

    @Test
    public void testServer() {
        vertx = Vertx.vertx();
        client = vertx.createHttpClient();
        VertxSpring.deploy(vertx,
                "test-context.xml",
                new HttpServerOptions()
                    .setPort(8080),
                (deployRes) -> {
            if (deployRes.failed()) {
                deployRes.cause().printStackTrace();
                assertTrue(deployRes.succeeded());
            }
            testAll((testRes) ->
                vertx.undeploy(deployRes.result(), (undeployRes) -> {
                    testComplete();
                })
            );
        });
        await();
    }

    private void testAll(Handler<AsyncResult<CompositeFuture>> handler) {
        CompositeFuture.all(
            Arrays.asList(testGet()))
                .setHandler(handler);
    }

    private Future testGet() {
        Future<String> future = Future.future();
        client.getNow(8080, "127.0.0.1", "/test", (response) -> {
            if (response.statusCode() != 200) {
                future.fail(response.statusMessage());
                return;
            }
            response
                .exceptionHandler((e) -> future.fail(e))
                .bodyHandler((buff) -> {
                    future.complete(buff.toString());
                });
        });
        return future;
    }

}
