package com.abc.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.datasource.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.abc")
public class HibernateConfig {

    @Autowired
    private DataSource dataSource;

    // Cấu hình SessionFactory
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource); // Đưa DataSource vào
        sessionFactoryBean.setPackagesToScan("com.abc.entities");  // Đảm bảo rằng bạn đã quét package chứa các entity
        sessionFactoryBean.setHibernateProperties(hibernateProperties());
        return sessionFactoryBean;
    }

    private java.util.Properties hibernateProperties() {
        java.util.Properties properties = new java.util.Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.hbm2ddl.auto", "update");  // Hoặc "create" nếu muốn tạo bảng khi khởi động
        return properties;
    }

    // Cấu hình Transaction Manager
    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());  // Đặt SessionFactory vào TransactionManager
        return transactionManager;
    }
}
