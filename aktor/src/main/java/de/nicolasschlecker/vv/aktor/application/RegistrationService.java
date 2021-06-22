package de.nicolasschlecker.vv.aktor.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nicolasschlecker.vv.aktor.domain.Aktor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;

@Service
public class RegistrationService implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    @Value("${AktorId}")
    private Long aktorId;

    @Value("${SmartHomeServiceRegistrationURL}")
    private String smartHomeServiceRegistrationURL;


    @Value("${local.server.port}")
    private int port;

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public RegistrationService(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = Jackson2ObjectMapperBuilder
                .json()
                .dateFormat(DateFormat.getDateTimeInstance())
                .build();
    }

    @Override
    public void run() {
        final var name = "VV-DemoSensor";
        final var location = "VV-DemoSensor";
        String serviceUrl;
        try {
            final var ip = InetAddress.getLocalHost().getHostAddress();
            serviceUrl = String.format("http://%s:%s/api/v1/status?");
        } catch (UnknownHostException e) {
            logger.error("Couldn't get local ip address.");
        }
        var counter = 0;

        while (Thread.currentThread().isAlive()) {
            try {
                logger.info("Registering Aktor \"{}\" with id \"{}\" at \"{}\"", name, aktorId, location);
                final var aktor = new Aktor(aktorId, name, location, "");
                final var request = new Request.Builder()
                        .post(RequestBody.create(MediaType.get("application/json"), objectMapper.writeValueAsString(aktor)))
                        .url(smartHomeServiceRegistrationURL)
                        .build();
                sendRequest(request);
                logger.info("Aktor registered.");
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                logger.error("Couldn't register sensor, retrying in 10 seconds, tries: {}", ++counter, e);
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException iE) {
                    Thread.currentThread().interrupt();
                }
            }
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
