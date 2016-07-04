package io.vertx.ext.spring.impl;

import io.vertx.core.Vertx;


public class VertxHolder {

  private static ThreadLocal<Vertx> threadLocal = new ThreadLocal<>();

  public static Vertx get() {
    return threadLocal.get();
  }

  public static void set(Vertx vertx) {
    threadLocal.set(vertx);
  }

}
