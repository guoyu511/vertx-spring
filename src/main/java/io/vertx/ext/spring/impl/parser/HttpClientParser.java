package io.vertx.ext.spring.impl.parser;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import io.vertx.ext.spring.impl.factory.HttpClientFactory;

/**
 * Created by guoyu on 16/3/14.
 */
public class HttpClientParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionRegistry registry = parserContext.getRegistry();
        GenericBeanDefinition def = new GenericBeanDefinition();
        MutablePropertyValues prop = new MutablePropertyValues();
        def.setBeanClass(HttpClientFactory.class);
        prop.addPropertyValue("keepAlive", element.getAttribute("keep-alive"));
        prop.addPropertyValue("maxPoolSize", element.getAttribute("max-pool-size"));
        prop.addPropertyValue("pipelining", element.getAttribute("pipelining"));
        def.setPropertyValues(prop);
        registry.registerBeanDefinition("httpClient", def);
        return null;
    }

}
