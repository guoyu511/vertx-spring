package io.vertx.ext.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.function.Supplier;

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

    static void deploy(Vertx vertx, Supplier<ApplicationContext> contextSupplier, HttpServerOptions options) {
        deploy(vertx, contextSupplier, options, (ar) -> {});
    }

    static void deploy(Vertx vertx, Supplier<ApplicationContext> contextSupplier, HttpServerOptions options, Handler<AsyncResult<String>> handler) {
        vertx.deployVerticle(new SpringHttpVerticle(contextSupplier, options), handler);
    }

}
