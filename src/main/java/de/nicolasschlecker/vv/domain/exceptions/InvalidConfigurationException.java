package de.nicolasschlecker.vv.domain.exceptions;

public final class InvalidConfigurationException extends RuntimeException {
    public InvalidConfigurationException(String message) {
        super(message);
    }
}
