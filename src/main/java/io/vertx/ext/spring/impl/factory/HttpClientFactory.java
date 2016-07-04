package io.vertx.ext.spring.impl.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;

public class HttpClientFactory implements FactoryBean<HttpClient> {

  @Autowired
  Vertx vertx;

  private boolean keepAlive;

  private int maxPoolSize;

  private boolean pipelining;

  public void setKeepAlive(boolean keepAlive) {
    this.keepAlive = keepAlive;
  }

  public void setMaxPoolSize(int maxPoolSize) {
    this.maxPoolSize = maxPoolSize;
  }

  public void setPipelining(boolean pipelining) {
    this.pipelining = pipelining;
  }

  @Override
  public HttpClient getObject() throws Exception {

    return vertx.createHttpClient(new HttpClientOptions()
      .setUsePooledBuffers(true)
      .setKeepAlive(keepAlive)
      .setMaxPoolSize(maxPoolSize)
      .setPipelining(pipelining));
  }

  @Override
  public Class<?> getObjectType() {
    return HttpClient.class;
  }

  @Override
  public boolean isSingleton() {
    return true;
  }

}
