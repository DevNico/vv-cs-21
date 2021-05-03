package de.nicolasschlecker.vv;

import de.nicolasschlecker.vv.common.LoggerFactory;
import de.nicolasschlecker.vv.common.config.ApplicationConfig;
import de.nicolasschlecker.vv.net.MeasurementServer;

import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args) {
        final var logger = LoggerFactory.getLogger(Main.class);

        final var runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        logger.info("=== System information ===");
        logger.info(() -> String.format("JVM-Version: %s", runtimeMXBean.getVmVersion()));
        logger.info(() -> String.format("JVM-Name: %s", runtimeMXBean.getVmName()));
        logger.info(() -> String.format("JVM-Vendor: %s", runtimeMXBean.getVmVendor()));
        logger.info(() -> String.format("Spec-Name: %s", runtimeMXBean.getSpecName()));
        logger.info(() -> String.format("Spec-Vendor: %s", runtimeMXBean.getSpecVendor()));
        logger.info(() -> String.format("Spec-Version: %s", runtimeMXBean.getSpecVersion()));

        final var config = ApplicationConfig.getConfig();
        logger.info("=== Application Configuration ===");
        logger.info(() -> String.format("Port set to: %s", config.getPort()));
        logger.info(() -> String.format("Logfile location set to: %s", config.getLogFilePath().toAbsolutePath()));
        logger.info(() -> String.format("LogLevel set to: %s", config.getLogLevel()));
        logger.info(() -> String.format("Measurement file location set to: %s", config.getJsonFilePath()));

        logger.info("=== Application Startup ===");
        final var measurementServer = new MeasurementServer(config.getPort());
        measurementServer.start();
    }
}
