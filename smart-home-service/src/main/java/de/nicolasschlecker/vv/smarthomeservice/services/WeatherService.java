package de.nicolasschlecker.vv.smarthomeservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nicolasschlecker.vv.smarthomeservice.application.dependencies.weatherservice.IWeatherService;
import de.nicolasschlecker.vv.smarthomeservice.domain.WeatherServiceUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class WeatherService implements IWeatherService {

    private final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Value("${WEATHER_SERVICE_AUTH_URL}")
    private String weatherServiceAuthUrl;

    @Value("${WEATHER_SERVICE_DATA_URL}")
    private String weatherServiceDataUrl;

    @Value("${WEATHER_SERVICE_USERNAME")
    private String weatherServiceUsername;

    @Value("${WEATHER_SERVICE_PASSWORD}")
    private String weatherServicePassword;

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public WeatherService(OkHttpClient okHttpClient, ObjectMapper objectMapper) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = objectMapper;
    }

    private Optional<String> getApiKey() {
        try {
            final var credentials = new WeatherServiceUser(weatherServiceUsername, weatherServicePassword);
            final var credentialsJson = objectMapper.writeValueAsString(credentials);

            final var
                    request = new Request.Builder()
                    .post(RequestBody.create(MediaType.get("application/json"), credentialsJson))
                    .url(weatherServiceAuthUrl)
                    .build();
            final var response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {
                assert response.body() != null;
                return Optional.of(response.body().string());
            }
        } catch (IOException ignored) {
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getWeather() {
        logger.info("Requesting weather forecast from {}", weatherServiceDataUrl);

        final var optionalApiKey = getApiKey();
        if (optionalApiKey.isEmpty()) {
            logger.info("Weather service authentication failed.");
            return Optional.empty();
        }
        final var apiKey = optionalApiKey.get();

        try {
            final var request = new Request.Builder()
                    .get()
                    .header("Authorization", String.format("Bearer %s", apiKey))
                    .url(weatherServiceDataUrl)
                    .build();
            final var response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {
                assert response.body() != null;
                final var responseString = response.body().string();
                logger.info("Got Weather forecast: {}", responseString);
                return Optional.of(responseString);
            }
        } catch (IOException e) {
            logger.error("Couldn't get Weather forecast", e);
        }
        return Optional.empty();
    }
}
