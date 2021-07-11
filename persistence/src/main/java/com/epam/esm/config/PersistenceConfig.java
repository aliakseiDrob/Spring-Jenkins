package com.epam.esm.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@PropertySource("classpath:persistence.properties")
@EnableJpaRepositories(basePackages = "com.epam.esm.repository")
@EntityScan(basePackages = {"com.epam.esm.entity","com.epam.esm.audit"})
@ComponentScan(basePackages = "com.epam.esm.repository")
public class PersistenceConfig {
}
