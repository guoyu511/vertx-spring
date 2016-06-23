package io.vertx.ext.spring.impl;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import io.vertx.ext.spring.impl.parser.HttpClientParser;
import io.vertx.ext.spring.impl.parser.JdbcParser;
import io.vertx.ext.spring.impl.parser.RedisClientParser;
import io.vertx.ext.spring.impl.parser.RouterParser;

public class NamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("router", new RouterParser());
        registerBeanDefinitionParser("http-client", new HttpClientParser());
        registerBeanDefinitionParser("jdbc", new JdbcParser());
        registerBeanDefinitionParser("redis", new RedisClientParser());
    }

}