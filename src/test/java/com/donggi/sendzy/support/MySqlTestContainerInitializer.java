package com.donggi.sendzy.support;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MySQLContainer;

public class MySqlTestContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final MySQLContainer<?> MYSQL =
        new MySQLContainer<>("mysql:8.0.33")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    static {
        MYSQL.start();
    }

    @Override
    public void initialize(final ConfigurableApplicationContext context) {
        TestPropertyValues.of(
            "spring.datasource.url="      + MYSQL.getJdbcUrl(),
            "spring.datasource.username=" + MYSQL.getUsername(),
            "spring.datasource.password=" + MYSQL.getPassword(),
            "spring.datasource.driver-class-name=" + MYSQL.getDriverClassName()
        ).applyTo(context.getEnvironment());
    }
}
