package de.nicolasschlecker.vvsmarthomeservice.application;

import de.nicolasschlecker.vvsmarthomeservice.domain.aktor.PersistentAktor;
import de.nicolasschlecker.vvsmarthomeservice.domain.aktor.ShutterStatus;
import de.nicolasschlecker.vvsmarthomeservice.repositories.AktorRepository;
import de.nicolasschlecker.vvsmarthomeservice.repositories.RuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class RuleEngine {
    private final Logger logger = LoggerFactory.getLogger(RuleEngine.class);

    private static final int RULE_ENGINE_SLEEP = 30 * 1000;

    private final RuleRepository ruleRepository;
    private final AktorRepository aktorRepository;

    @Autowired
    public RuleEngine(RuleRepository ruleRepository, AktorRepository aktorRepository) {
        this.ruleRepository = ruleRepository;
        this.aktorRepository = aktorRepository;
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
