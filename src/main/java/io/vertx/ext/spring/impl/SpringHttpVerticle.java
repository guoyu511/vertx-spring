package io.vertx.ext.spring.impl;

import org.springframework.context.ApplicationContext;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.spring.ContextFactory;
import io.vertx.ext.web.Router;

/**
 * Created by guoyu on 16/6/23.
 */
public class SpringHttpVerticle extends AbstractVerticle {

  private ContextFactory factory;

  private ApplicationContext applicationContext;

  private HttpServerOptions options;

  public SpringHttpVerticle(ContextFactory factory, HttpServerOptions options) {
    this.factory = factory;
    this.options = options;
  }

  @Override
  public void start() throws Exception {
    super.start();
    VertxHolder.set(getVertx());
    applicationContext = factory.create();
    Router router = applicationContext.getBean(Router.class);
    vertx.createHttpServer(options)
      .requestHandler(router::accept)
      .listen();
  }
}
