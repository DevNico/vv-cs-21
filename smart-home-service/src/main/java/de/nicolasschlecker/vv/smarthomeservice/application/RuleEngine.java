package de.nicolasschlecker.vv.smarthomeservice.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nicolasschlecker.vv.smarthomeservice.application.dependencies.weatherservice.IWeatherService;
import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.PersistentAktor;
import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.ShutterStatus;
import de.nicolasschlecker.vv.smarthomeservice.domain.rule.PersistentRule;
import de.nicolasschlecker.vv.smarthomeservice.repositories.AktorRepository;
import de.nicolasschlecker.vv.smarthomeservice.repositories.RuleRepository;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;

@Component
public class RuleEngine {
    private final Logger logger = LoggerFactory.getLogger(RuleEngine.class);

    private static final int RULE_ENGINE_SLEEP = 30 * 1000;
    private static final String WEATHER_SUNNY = "Sunny";

    private final IWeatherService weatherService;
    private final RuleRepository ruleRepository;
    private final AktorRepository aktorRepository;
    private final ObjectMapper objectMapper;
    private final OkHttpClient okHttpClient;

    @Autowired
    public RuleEngine(IWeatherService weatherService, RuleRepository ruleRepository, AktorRepository aktorRepository, ObjectMapper objectMapper, OkHttpClient okHttpClient) {
        this.weatherService = weatherService;
        this.ruleRepository = ruleRepository;
        this.aktorRepository = aktorRepository;
        this.objectMapper = objectMapper;
        this.okHttpClient = okHttpClient;
    }

    private boolean updateAktor(PersistentAktor aktor) {
        try {
            final var url = String.format("%s?status=%s", aktor.getServiceUrl(), objectMapper.writeValueAsString(aktor.getCurrentState()).replaceAll("\"", ""));

            logger.info("Updating Aktor \"{}\" at \"{}\"", aktor.getId(), url);
            final var request = new Request.Builder()
                    .post(RequestBody.create(MediaType.get("application/json"), "{}"))
                    .url(url)
                    .build();
            final var response = okHttpClient.newCall(request).execute();

            if (!response.isSuccessful()) {
                logger.error("Aktor sent unsuccessful response");
            } else {
                logger.info("Aktor updated.");
                return true;
            }
        } catch (IOException e) {
            logger.error("Couldn't update aktor", e);
        }
        return false;
    }

    private PersistentAktor checkRule(PersistentRule rule) {
        final var aktor = rule.getAktor();
        final var sensor = rule.getSensor();
        final var sensorData = sensor.getSensorData();

        if (sensorData.getCurrentValue() > rule.getThreshold() && aktor.getCurrentState() != ShutterStatus.CLOSED) {
            logger.info(
                    "\"{}\" in \"{}\": {} (Threshold: {}) => Changing status to ShutterStatus.CLOSED",
                    sensor.getName(),
                    sensor.getLocation(),
                    sensorData.getCurrentValue(),
                    rule.getThreshold()
            );
            aktor.setCurrentState(ShutterStatus.CLOSED);
            if (updateAktor(aktor)) {
                return aktor;
            }
        } else if (sensorData.getCurrentValue() <= rule.getThreshold() && aktor.getCurrentState() != ShutterStatus.OPEN) {
            logger.info(
                    "\"{}\" in \"{}\": {} (Threshold: {}) => Changing status to ShutterStatus.OPEN",
                    sensor.getName(),
                    sensor.getLocation(),
                    sensorData.getCurrentValue(),
                    rule.getThreshold()
            );
            aktor.setCurrentState(ShutterStatus.OPEN);
            if (updateAktor(aktor)) {
                return aktor;
            }
        }

        return null;
    }

    private void checkRules() {
        final var rules = ruleRepository.findAll();
        final var aktorsToUpdate = new LinkedList<PersistentAktor>();

        for (final var rule : rules) {
            final var updatedAktor = checkRule(rule);

            if (updatedAktor != null) {
                aktorsToUpdate.add(updatedAktor);
            }
        }

        if (aktorsToUpdate.isEmpty()) {
            logger.info("Rules checked, no action necessary.");
        } else {
            aktorRepository.saveAll(aktorsToUpdate);
            logger.info("Rules checked, updated {} aktors.", aktorsToUpdate.size());
        }
    }

    private boolean checkWeather() {
        final var weather = weatherService.getWeather();

        if (weather.isEmpty()) {
            logger.error("Couldn't get Weather for Rules");
            return false;
        }

        final var weatherString = weather.get();

        logger.info("Weather: {}", weatherString);

        if (!weatherString.equals(WEATHER_SUNNY)) {
            logger.info("Rules checked, weather is not {}", WEATHER_SUNNY);
            return false;
        }

        return true;
    }

    @Scheduled(fixedRate = RULE_ENGINE_SLEEP)
    public void ruleEngine() {
        logger.info("RuleEngine started");

        while (Thread.currentThread().isAlive()) {
            logger.info("Checking rules...");

            if (checkWeather()) {
                checkRules();
            }

            try {
                Thread.sleep(RULE_ENGINE_SLEEP);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
