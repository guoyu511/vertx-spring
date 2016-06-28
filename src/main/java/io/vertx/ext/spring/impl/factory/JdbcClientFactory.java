package io.vertx.ext.spring.impl.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.sql.DataSource;

import io.vertx.core.Vertx;
import io.vertx.ext.jdbc.JDBCClient;

public class JdbcClientFactory implements InitializingBean, ApplicationContextAware, FactoryBean<JDBCClient> {

    @Autowired Vertx vertx;

    private String dataSourceRef;

    private DataSource dataSource;

    private ApplicationContext applicationContext;

    public void setDataSourceRef(String dataSourceRef) {
        this.dataSourceRef = dataSourceRef;
    }

    @Override
    public JDBCClient getObject() throws Exception {
        return JDBCClient.create(vertx, dataSource);
    }

    @Override
    public Class<?> getObjectType() {
        return JDBCClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Object bean = applicationContext.getBean(dataSourceRef);
        if (!DataSource.class.isInstance(bean)) {
            throw new InvalidPropertyException(DataSource.class, "data-source-ref", "Invalid DataSource");
        }
        dataSource = (DataSource)bean;
    }
}
