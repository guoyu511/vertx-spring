package io.vertx.ext.spring;

import org.junit.Test;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.test.core.AsyncTestBase;

public class SimpleTest extends AsyncTestBase {

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
          return;
        }
        testAll((testRes) ->
          vertx.undeploy(deployRes.result(), (undeployRes) -> {
            testComplete();
          })
        );
      });
    await();
  }

  private void testAll(Handler<AsyncResult<Void>> handler) {
    testPut("a", "1", (ar) ->
      testGet("a", "1", handler)

    );
  }

  private void testPut(String key, String value, Handler<AsyncResult<Void>> handler) {
    client.put(8080, "127.0.0.1", "/test?key=" + key + "&value=" + value, (response) -> {
      if (response.statusCode() != 200) {
        handler.handle(Future.failedFuture(response.statusMessage()));
        return;
      }
      response
        .exceptionHandler((e) ->
          handler.handle(Future.failedFuture(e))
        )
        .bodyHandler((buff) ->
          handler.handle(Future.succeededFuture())
        );
    }).end();
  }

  private void testGet(String key, String value, Handler<AsyncResult<Void>> handler) {
    client.getNow(8080, "127.0.0.1", "/test?key=" + key, (response) -> {
      if (response.statusCode() != 200) {
        handler.handle(Future.failedFuture(response.statusMessage()));
        return;
      }
      response
        .exceptionHandler((e) ->
          handler.handle(Future.failedFuture(e))
        )
        .bodyHandler((buff) -> {
          assertEquals(value, buff.toString());
          handler.handle(Future.succeededFuture());
        });
    });
  }

}
