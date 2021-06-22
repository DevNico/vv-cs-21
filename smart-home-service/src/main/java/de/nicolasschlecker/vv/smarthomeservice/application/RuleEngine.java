package de.nicolasschlecker.vv.smarthomeservice.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.PersistentAktor;
import de.nicolasschlecker.vv.smarthomeservice.domain.aktor.ShutterStatus;
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

    private final RuleRepository ruleRepository;
    private final AktorRepository aktorRepository;
    private final ObjectMapper objectMapper;
    private final OkHttpClient okHttpClient;

    @Autowired
    public RuleEngine(RuleRepository ruleRepository, AktorRepository aktorRepository, ObjectMapper objectMapper, OkHttpClient okHttpClient) {
        this.ruleRepository = ruleRepository;
        this.aktorRepository = aktorRepository;
        this.objectMapper = objectMapper;
        this.okHttpClient = okHttpClient;
    }

    private void updateAktor(PersistentAktor aktor) {
        try {
            logger.info("Updating Aktor \"{}\" with id \"{}\" at \"{}\"", aktor.getName(), aktor.getId(), aktor.getLocation());
            final var request = new Request.Builder()
                    .post(RequestBody.create(MediaType.get("application/json"), "{}"))
                    .url(String.format("%s?status=%s", aktor.getServiceUrl(), objectMapper.writeValueAsString(aktor.getCurrentState())))
                    .build();
            sendRequest(request);
            logger.info("Aktor updated.");
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            logger.error("Couldn't update aktor");
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

    @Scheduled(fixedRate = RULE_ENGINE_SLEEP)
    public void checkRules() {
        logger.info("RuleEngine started");

        while (Thread.currentThread().isAlive()) {
            logger.info("Checking rules...");
            final var rules = ruleRepository.findAll();
            final var aktorsToUpdate = new LinkedList<PersistentAktor>();

            for (final var rule : rules) {
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
                    updateAktor(aktor);
                    aktorsToUpdate.add(aktor);
                } else if (sensorData.getCurrentValue() <= rule.getThreshold() && aktor.getCurrentState() != ShutterStatus.OPEN) {
                    logger.info(
                            "\"{}\" in \"{}\": {} (Threshold: {}) => Changing status to ShutterStatus.OPEN",
                            sensor.getName(),
                            sensor.getLocation(),
                            sensorData.getCurrentValue(),
                            rule.getThreshold()
                    );
                    aktor.setCurrentState(ShutterStatus.OPEN);
                    updateAktor(aktor);
                    aktorsToUpdate.add(aktor);
                }
            }

            if (aktorsToUpdate.isEmpty()) {
                logger.info("Rules checked, no action necessary.");
            } else {
                aktorRepository.saveAll(aktorsToUpdate);
                logger.info("Rules checked, updated {} aktors.", aktorsToUpdate.size());
            }


            try {
                Thread.sleep(RULE_ENGINE_SLEEP);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
