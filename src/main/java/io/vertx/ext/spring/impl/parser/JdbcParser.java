package io.vertx.ext.spring.impl.parser;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import io.vertx.ext.spring.impl.factory.JdbcClientFactory;

/**
 * Created by guoyu on 16/3/14.
 */
public class JdbcParser implements BeanDefinitionParser {

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        BeanDefinitionRegistry registry = parserContext.getRegistry();
        GenericBeanDefinition def = new GenericBeanDefinition();
        MutablePropertyValues prop = new MutablePropertyValues();
        def.setBeanClass(JdbcClientFactory.class);
        String id = element.getAttribute("id");
        prop.addPropertyValue("dataSourceRef", element.getAttribute("data-source-ref"));
        def.setPropertyValues(prop);
        registry.registerBeanDefinition(id, def);
        return null;
    }

}
