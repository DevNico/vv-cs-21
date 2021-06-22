package de.nicolasschlecker.vv.smarthomeservice.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.nicolasschlecker.vv.smarthomeservice.application.dependencies.weatherservice.IWeatherForecastProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@Profile({"production"})
public class WeatherForecastProvider implements IWeatherForecastProvider {

    private final Logger logger = LoggerFactory.getLogger(WeatherForecastProvider.class);

    @Value("${WEATHER_SERVICE_AUTH_URL}")
    private String weatherServiceAuthUrl;

    @Value("${WEATHER_SERVICE_DATA_URL}")
    private String weatherServiceDataUrl;

    @Value("${WEATHER_SERVICE_USERNAME}")
    private String weatherServiceUsername;

    @Value("${WEATHER_SERVICE_PASSWORD}")
    private String weatherServicePassword;

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    @Autowired
    public WeatherForecastProvider(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        this.objectMapper = Jackson2ObjectMapperBuilder.json().build();
    }

    @Data
    @AllArgsConstructor
    private static class User {
        private String username;
        private String password;
    }

    private String buildCredentials(String username, String password) throws JsonProcessingException {
        final var user = new User(username, password);
        return objectMapper.writeValueAsString(user);
    }

    private Optional<String> getApiKey() {
        try {
            final var credentials = buildCredentials(weatherServiceUsername, weatherServicePassword);
            final var logCredentials = buildCredentials(weatherServiceUsername, "*".repeat(weatherServicePassword.length()));

            logger.info("Requesting api token for WeatherService from {} with credentials {}", weatherServiceAuthUrl, logCredentials);

            final var
                    request = new Request.Builder()
                    .post(RequestBody.create(MediaType.get("application/json"), credentials))
                    .url(weatherServiceAuthUrl)
                    .build();
            final var response = okHttpClient.newCall(request).execute();

            if (response.isSuccessful()) {
                assert response.body() != null;
                return Optional.of(response.body().string());
            } else {
                throw new IOException(response.message());
            }
        } catch (IOException e) {
            logger.error("Failed to get api key for WeatherService", e);
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
                final var weatherResponseNode = objectMapper.readValue(responseString, ObjectNode.class);
                final var summary = weatherResponseNode.get("summary").asText();
                logger.info("Got Weather forecast: {}", summary);
                return Optional.of(summary);
            }
        } catch (IOException e) {
            logger.error("Couldn't get Weather forecast", e);
        }
        return Optional.empty();
    }
}
