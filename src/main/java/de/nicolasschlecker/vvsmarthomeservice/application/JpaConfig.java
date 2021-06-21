package de.nicolasschlecker.vvsmarthomeservice.application;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
class JpaConfig {
    @Bean
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.postgresql.Driver")
                .url(String.format("jdbc:postgresql://%s:%s/%s",
                        System.getenv("DB_HOST"),
                        System.getenv("DB_PORT"),
                        System.getenv("DB_NAME"))
                )
                .username(System.getenv("DB_USERNAME"))
                .password(System.getenv("DB_PASSWORD"))
                .build();
    }
}
