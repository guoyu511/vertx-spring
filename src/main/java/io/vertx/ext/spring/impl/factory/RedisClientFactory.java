package io.vertx.ext.spring.impl.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import io.vertx.core.Vertx;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

public class RedisClientFactory implements FactoryBean<RedisClient> {

    @Autowired Vertx vertx;

    private String host;

    private int port;

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

}
