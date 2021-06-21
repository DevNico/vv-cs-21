package de.nicolasschlecker.vvsmarthomeservice.application;

import de.nicolasschlecker.vvsmarthomeservice.domain.aktor.PersistentAktor;
import de.nicolasschlecker.vvsmarthomeservice.domain.aktor.ShutterStatus;
import de.nicolasschlecker.vvsmarthomeservice.repositories.AktorRepository;
import de.nicolasschlecker.vvsmarthomeservice.repositories.RuleRepository;
import de.nicolasschlecker.vvsmarthomeservice.repositories.SensorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
@Scope("application")
public class RuleEngine implements Runnable {
    private final Logger LOGGER = LoggerFactory.getLogger(RuleEngine.class);

    private static final int RULE_ENGINE_SLEEP = 30 * 1000;

    private final RuleRepository ruleRepository;
    private final AktorRepository aktorRepository;
    private final SensorRepository sensorRepository;

    @Autowired
    public RuleEngine(SensorRepository sensorRepository, RuleRepository ruleRepository, AktorRepository aktorRepository) {
        this.sensorRepository = sensorRepository;
        this.ruleRepository = ruleRepository;
        this.aktorRepository = aktorRepository;
    }

    @Override
    public void run() {
        LOGGER.info("RuleEngine started");

        while (Thread.currentThread().isAlive()) {
            final var rules = ruleRepository.findAll();
            final var aktorsToUpdate = new LinkedList<PersistentAktor>();

            for (final var rule : rules) {
                final var aktor = rule.getAktor();

                if (rule.getSensor().getSensorData().getCurrentValue() > rule.getThreshold() && rule.getAktor().getCurrentState() != ShutterStatus.CLOSED) {
                    aktor.setCurrentState(ShutterStatus.CLOSED);
                    aktorsToUpdate.add(aktor);
                } else if (rule.getSensor().getSensorData().getCurrentValue() <= rule.getThreshold() && rule.getAktor().getCurrentState() != ShutterStatus.OPEN) {
                    aktor.setCurrentState(ShutterStatus.OPEN);
                    aktorsToUpdate.add(aktor);
                }
            }

            aktorRepository.saveAll(aktorsToUpdate);

            try {
                Thread.sleep(RULE_ENGINE_SLEEP);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
