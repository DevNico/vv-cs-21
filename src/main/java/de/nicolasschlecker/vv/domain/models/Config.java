package de.nicolasschlecker.vv.domain.models;


import java.nio.file.Path;
import java.util.Objects;
import java.util.logging.Level;

public class Config {
    private final int port;
    private final Path logFilePath;
    private final Level logLevel;

    public Config(int port, Path logFilePath, Level logLevel) {
        this.port = port;
        this.logFilePath = logFilePath;
        this.logLevel = logLevel;
    }

    public int getPort() {
        return port;
    }

    public Path getLogFilePath() {
        return logFilePath;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    @Override
    public String toString() {
        return "Config{" +
                "port=" + port +
                ", logFilePath=" + logFilePath +
                ", logLevel=" + logLevel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Config that = (Config) o;
        return getPort() == that.getPort() && Objects.equals(getLogFilePath(), that.getLogFilePath()) && Objects.equals(getLogLevel(), that.getLogLevel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPort(), getLogFilePath(), getLogLevel());
    }
}
