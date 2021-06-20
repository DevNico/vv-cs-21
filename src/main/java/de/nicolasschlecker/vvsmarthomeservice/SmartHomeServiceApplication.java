package de.nicolasschlecker.vvsmarthomeservice;

import de.nicolasschlecker.vvsmarthomeservice.controller.SensorController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;

@SpringBootApplication
@EnableSwagger2
@Import({SpringDataRestConfiguration.class, BeanValidatorPluginsConfiguration.class})
@ComponentScan(basePackageClasses = {
        SensorController.class
})
public class SmartHomeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartHomeServiceApplication.class, args);
    }

    @Bean
    public Docket smarthomeServiceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .directModelSubstitute(LocalDate.class, String.class);
    }

}
