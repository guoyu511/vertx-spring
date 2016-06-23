package io.vertx.ext.spring.impl.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import io.vertx.core.Vertx;
import io.vertx.ext.spring.impl.VertxHolder;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

public class RedisClientFactory implements InitializingBean, FactoryBean<RedisClient> {

    private String host;

    private int port;

    private Vertx vertx;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public RedisClient getObject() throws Exception {
        return RedisClient.create(vertx, new RedisOptions()
            .setHost(host)
            .setPort(port)
            .setTcpKeepAlive(true)
            .setTcpNoDelay(true));
    }

    @Override
    public Class<?> getObjectType() {
        return RedisClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        vertx = VertxHolder.get();
    }
}
