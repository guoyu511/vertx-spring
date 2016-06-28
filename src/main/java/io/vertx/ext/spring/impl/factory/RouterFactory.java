package io.vertx.ext.spring.impl.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Method;
import java.util.Map;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.spring.annotation.RouterHandler;
import io.vertx.ext.spring.annotation.VertxRouter;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class RouterFactory implements ApplicationContextAware, FactoryBean<Router>{

    private static Logger logger = LoggerFactory.getLogger(RouterFactory.class);

    @Autowired Vertx vertx;

    private ApplicationContext applicationContext;

    @Override
    public Router getObject() throws Exception {
        Router router = Router.router(vertx);
        Map<String, Object> handlers = applicationContext.getBeansWithAnnotation(
                VertxRouter.class);
        handlers.values().forEach((handler) -> registerHandlers(router, handler));
        return router;
    }

    @Override
    public Class<?> getObjectType() {
        return Router.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static void registerHandlers(Router router, Object handler) {
        VertxRouter controller = handler.getClass().getAnnotation(VertxRouter.class);
        for (Method method : handler.getClass().getMethods()) {
            RouterHandler mapping = method.getAnnotation(RouterHandler.class);
            if (mapping != null) {
                if (method.getParameterTypes().length == 1 &&
                    method.getParameterTypes()[0].equals(RoutingContext.class)) {
                    registerMethod(router, handler, method, controller, mapping);
                } else {
                    throw new UnsupportedOperationException(
                            "Handler method " +
                            handler.getClass().getSimpleName() + "." + method.getName() +
                            "should only be declared with 1 parameter " +
                            RoutingContext.class);
                }
            }
        }
    }

    private static void registerMethod(Router router,
               Object handler, Method method,
               VertxRouter controller, RouterHandler mapping) {
        if (mapping.method() == HttpMethod.POST || mapping.method() == HttpMethod.PUT) {
            router.route(mapping.method(), controller.value() + mapping.value())
                .handler(BodyHandler.create());
        }
        if (mapping.worker()) {
            router.route(mapping.method(), controller.value() + mapping.value())
                .blockingHandler(new RouterHandlerImpl(handler, method, mapping.method()))
                .failureHandler((ctx) -> {
                    logger.error(ctx.failure());
                    ctx.response()
                        .setStatusCode(500)
                        .setStatusMessage(ctx.failure().getMessage());
                });
        } else {
            router.route(mapping.method(), controller.value() + mapping.value())
                .handler(new RouterHandlerImpl(handler, method, mapping.method()))
                .failureHandler((ctx) -> {
                    logger.error(ctx.failure());
                    ctx.response()
                        .setStatusCode(500)
                        .setStatusMessage(ctx.failure().getMessage());
                });
        }
        logger.info("Register handler " +
                mapping.method() + " " +
                controller.value() + mapping.value() +
                " on " +
                handler.getClass().getSimpleName() +
                "." +
                method.getName());
    }

    private static class RouterHandlerImpl implements Handler<RoutingContext> {

        Object handler;

        Method method;

        HttpMethod httpMethod;

        RouterHandlerImpl(Object handler, Method method, HttpMethod httpMethod) {
            this.handler = handler;
            this.method = method;
            this.httpMethod = httpMethod;
        }

        @Override
        public void handle(RoutingContext context) {
            logger.debug(context.request().method().toString() + " " + context.request().uri());
            try {
                method.invoke(handler, context);
            } catch (Throwable e) {
                context.fail(e);
            }
        }
    }

}
