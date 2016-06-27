package io.vertx.ext.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.spring.impl.SpringHttpVerticle;


public interface VertxSpring {

    static void deploy(Vertx vertx, String configurationFile, HttpServerOptions options) {
        deploy(vertx, () -> new ClassPathXmlApplicationContext(configurationFile), options);
    }

    static void deploy(Vertx vertx, String configurationFile, HttpServerOptions options, Handler<AsyncResult<String>> handler) {
        deploy(vertx, () -> new ClassPathXmlApplicationContext(configurationFile), options, handler);
    }

    static void deploy(Vertx vertx, ContextFactory factory, HttpServerOptions options) {
        deploy(vertx, factory, options, (ar) -> {});
    }

    static void deploy(Vertx vertx, ContextFactory factory, HttpServerOptions options, Handler<AsyncResult<String>> handler) {
        vertx.deployVerticle(new SpringHttpVerticle(factory, options), handler);
    }

}
