package de.nicolasschlecker.vv.sensor.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nicolasschlecker.vv.sensor.domain.Sensor;
import de.nicolasschlecker.vv.sensor.domain.SensorData;
import de.nicolasschlecker.vv.sensor.domain.TemperatureUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class SensorService {
    private static final int SENSOR_SCHEDULE = 60 * 1000;
    private final Logger logger = LoggerFactory.getLogger(SensorService.class);

    @Value("${SensorId}")
    private Long sensorId;

    @Value("${SmartHomeServiceRegistrationURL}")
    private String smartHomeServiceRegistrationURL;

    @Value("${SmartHomeServicePublishURL}")
    private String smartHomeServicePublishURL;

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;
    private final Random random;
    private boolean registered;

    @Autowired
    public SensorService(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = Jackson2ObjectMapperBuilder
                .json()
                .dateFormat(DateFormat.getDateTimeInstance())
                .build();
        this.random = new Random();
        this.registered = false;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void log() {
        logger.info("SensorId: {}", sensorId);
        logger.info("SmartHomeServiceRegistrationURL: {}", smartHomeServiceRegistrationURL);
        logger.info("SmartHomeServicePublishURL: {}", smartHomeServicePublishURL);
    }

    private boolean register() {
        if (registered) {
            return true;
        }

        final var name = "VV-DemoSensor";
        final var location = "VV-DemoSensor";

        try {
            logger.info("Registering Sensor \"{}\" with id \"{}\" at \"{}\"", name, sensorId, location);
            final var sensor = new Sensor(sensorId, name, location);
            final var request = new Request.Builder()
                    .post(RequestBody.create(MediaType.get("application/json"), objectMapper.writeValueAsString(sensor)))
                    .url(smartHomeServiceRegistrationURL)
                    .build();
            sendRequest(request);

            registered = true;
            return true;
        } catch (IOException e) {
            logger.error("Couldn't register sensor, retrying in 30 seconds", e);
        }
        return false;
    }

    @Scheduled(fixedRate = SENSOR_SCHEDULE)
    public void sendStatus() {
        if (!register()) {
            return;
        }

        try {
            final var sensorData = new SensorData(random.nextInt(30), LocalDateTime.now(), TemperatureUnit.CELSIUS);
            final var sensorDataJson = objectMapper.writeValueAsString(sensorData);
            logger.info("Sending data to sensor with id \"{}\": \"{}\"", sensorId, sensorDataJson);
            final var request = new Request.Builder()
                    .post(RequestBody.create(MediaType.get("application/json"), sensorDataJson))
                    .url(smartHomeServicePublishURL)
                    .build();
            sendRequest(request);
        } catch (IOException e) {
            logger.error("Couldn't send data to sensor, retrying in 30 seconds", e);
        }
    }

    private void sendRequest(Request request) throws IOException {
        final var response = okHttpClient.newCall(request).execute();

        if (!response.isSuccessful()) {
            final var body = response.body();
            if (body != null) {
                logger.error(body.string());
            }
            throw new IOException();
        }
    }
}
