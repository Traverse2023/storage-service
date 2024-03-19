package com.traverse.storage.utils.exceptions.serializer;

public class InvalidTokenException extends Exception {
    public InvalidTokenException(final String message) {
        super(message);
    }

    public InvalidTokenException(final String message, final Exception exception) {
        super(message, exception);
    }
}
