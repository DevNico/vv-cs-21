package de.nicolasschlecker.vv.smarthomeservice;

import de.nicolasschlecker.vv.smarthomeservice.controller.SensorsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SmartHomeServiceApplicationTest {

    @Autowired
    private SensorsController sensorsController;

    @Test
    void contextLoads() {
        assertThat(sensorsController).isNotNull();
    }
}