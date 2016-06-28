package io.vertx.ext.spring.impl.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import io.vertx.ext.spring.impl.factory.VertxFactory;

/**
 * Created by guoyu on 16/6/28.
 */
public class VertxParser implements BeanDefinitionParser {
    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionRegistry registry = parserContext.getRegistry();
        GenericBeanDefinition def = new GenericBeanDefinition();
        def.setBeanClass(VertxFactory.class);
        registry.registerBeanDefinition("vertx", def);
        return def;
    }
}
