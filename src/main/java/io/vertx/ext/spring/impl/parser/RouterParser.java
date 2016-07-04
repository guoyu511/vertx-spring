package io.vertx.ext.spring.impl.parser;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionDefaults;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import io.vertx.ext.spring.annotation.VertxRouter;
import io.vertx.ext.spring.impl.factory.RouterFactory;

/**
 * Created by guoyu on 16/3/14.
 */
public class RouterParser implements BeanDefinitionParser {

  @Override
  public BeanDefinition parse(Element element, ParserContext parserContext) {
    BeanDefinitionRegistry registry = parserContext.getRegistry();
    ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(parserContext.getRegistry(), false);
    BeanDefinitionDefaults defaults = new BeanDefinitionDefaults();
    defaults.setLazyInit(true);
    scanner.setBeanDefinitionDefaults(defaults);
    String basePackage = element.getAttribute("base-package");
    scanner.addIncludeFilter(new AnnotationTypeFilter(VertxRouter.class));
    scanner.scan(StringUtils.tokenizeToStringArray(basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
    GenericBeanDefinition def = new GenericBeanDefinition();
    def.setBeanClass(RouterFactory.class);
    registry.registerBeanDefinition("vertx-spring-web-router", def);
    return def;
  }

}
