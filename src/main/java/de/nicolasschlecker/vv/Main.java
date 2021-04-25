package de.nicolasschlecker.vv;

import de.nicolasschlecker.vv.common.LoggerFactory;
import de.nicolasschlecker.vv.common.config.ApplicationConfig;
import de.nicolasschlecker.vv.common.config.EnvironmentConfigLoader;
import de.nicolasschlecker.vv.common.config.IConfigLoader;
import de.nicolasschlecker.vv.domain.exceptions.InvalidConfigurationException;

import java.lang.management.ManagementFactory;

public class Main {
    private static final IConfigLoader configLoader = new EnvironmentConfigLoader();

    public static void main(String[] args) {
        var logger = LoggerFactory.getLogger(Main.class);

        try {
            // Try to load configuration from the provided ConfigLoader
            ApplicationConfig.setConfig(configLoader.getConfig());
        } catch (InvalidConfigurationException e) {
            // Will log with default settings if the provided configuration was invalid but there is nothing we can do
            // about that.
            logger.warning(() -> String.format("Invalid configuration supplied: %s", e.getMessage()));
        } finally {
            // Get new logger because a custom configuration might have been supplied overriding the default settings.
            logger = LoggerFactory.getLogger(Main.class);
        }


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

        logger.info("=== Application Startup ===");
        // var measurementServer = new MeasurementServer(config.getPort());
        // measurementServer.start();
    }
}
