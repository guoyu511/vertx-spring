package io.vertx.ext.spring.impl;

import org.springframework.context.ApplicationContext;

import java.util.function.Supplier;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;

/**
 * Created by guoyu on 16/6/23.
 */
public class SpringHttpVerticle extends AbstractVerticle {

    private Supplier<ApplicationContext> contextSupplier;

    private ApplicationContext applicationContext;

    private HttpServerOptions options;

    public SpringHttpVerticle(Supplier<ApplicationContext> contextSupplier, HttpServerOptions options) {
        this.contextSupplier = contextSupplier;
        this.options = options;
    }

    @Override
    public void start() throws Exception {
        super.start();
        VertxHolder.set(getVertx());
        applicationContext = contextSupplier.get();
        Router router = applicationContext.getBean(Router.class);
        vertx.createHttpServer(options)
                .requestHandler(router::accept)
                .listen();
    }
}
