package de.nicolasschlecker.vv.smarthomeservice;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartHomeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartHomeServiceApplication.class, args);
    }

    @Bean
    public OpenAPI openAPI(@Value("1") String appVersion) {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("VV smart home service").version(appVersion));
    }
}
