package de.nicolasschlecker.vv.domain.exceptions;

public final class ConnectionException extends RuntimeException {
    public ConnectionException(String message) {
        super(message);
    }
}
