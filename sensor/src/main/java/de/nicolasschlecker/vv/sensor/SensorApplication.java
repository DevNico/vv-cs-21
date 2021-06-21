package de.nicolasschlecker.vv.sensor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class SensorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensorApplication.class, args);
    }

    private static final int SENSOR_SCHEDULE = 60 * 1000;

    @Value("${SensorId}")
    private String sensorId;

    @Value("${SmartHomeServiceRegistrationURL}")
    private String smartHomeServiceRegistrationURL;

    @Value("${SmartHomeServicePublishURL}")
    private String smartHomeServicePublishURL;

    @Scheduled(fixedRate = SENSOR_SCHEDULE)
    public void sendStatus() {
        //
    }

}
