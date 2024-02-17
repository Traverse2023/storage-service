package com.traverse.storage.utils.exceptions;

public class MessageCreationException extends RuntimeException {
    public MessageCreationException(final String message) {
        super(message);
    }
    public MessageCreationException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
