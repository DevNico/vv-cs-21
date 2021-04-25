package de.nicolasschlecker.vv.common;

import de.nicolasschlecker.vv.common.config.ApplicationConfig;
import de.nicolasschlecker.vv.domain.models.Config;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerFactory {
    private static Logger rootLogger;

    private LoggerFactory() {
    }

    public static java.util.logging.Logger getLogger(Class<?> clazz) {
        if (rootLogger == null) {
            System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tY-%1$tm-1%td %1$tH:%1$tM:%1$tS %2$s [%4$s] %5$s%6$s%n");
            rootLogger = Logger.getLogger("Root");

            final var config = ApplicationConfig.getConfig();

            rootLogger.setLevel(config.getLogLevel());

            try {
                rootLogger.addHandler(getFileHandler(config));
            } catch (IOException e) {
                rootLogger.severe("Couldn't add FileHandler to logger");
            }
        }

        final var logger = Logger.getLogger(clazz.getName());
        logger.setParent(rootLogger);

        return logger;
    }

    private static FileHandler getFileHandler(Config config) throws IOException {
        final var fileHandler = new FileHandler(config.getLogFilePath().toAbsolutePath().toString(), true);
        fileHandler.setLevel(config.getLogLevel());
        fileHandler.setEncoding("utf-8");
        fileHandler.setFormatter(new SimpleFormatter());
        return fileHandler;
    }
}
