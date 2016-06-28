package io.vertx.ext.spring.impl.factory;

import org.springframework.beans.factory.FactoryBean;

import io.vertx.core.Vertx;
import io.vertx.ext.spring.impl.VertxHolder;

/**
 * Created by guoyu on 16/6/28.
 */
public class VertxFactory implements FactoryBean<Vertx> {

    @Override
    public Vertx getObject() throws Exception {
        return VertxHolder.get();
    }

    @Override
    public Class<?> getObjectType() {
        return Vertx.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
