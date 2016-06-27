package io.vertx.ext.spring;

import org.springframework.context.ApplicationContext;

/**
 * Created by guoyu on 16/6/27.
 */
public interface ContextFactory {

    ApplicationContext create();

}
