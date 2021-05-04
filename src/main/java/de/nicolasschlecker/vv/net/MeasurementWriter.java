package de.nicolasschlecker.vv.net;

import de.nicolasschlecker.vv.common.LoggerFactory;
import de.nicolasschlecker.vv.common.config.ApplicationConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MeasurementWriter implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementWriter.class);

    private final BlockingQueue<String> blockingQueue;
    private final File jsonFile;

    public MeasurementWriter(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        this.jsonFile = ApplicationConfig.getConfig().getJsonFilePath().toFile();
    }

    @Override
    public void run() {
        try (final var fileWriter = new FileWriter(jsonFile, true);
             final var bufferedWriter = new BufferedWriter(fileWriter)) {
            while (!Thread.currentThread().isInterrupted()) {
                final var measurementJsonString = blockingQueue.take();
                LOGGER.info(() -> String.format("Writing new measurement %s", measurementJsonString));
                bufferedWriter.write(measurementJsonString);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (InterruptedException | IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }
}
