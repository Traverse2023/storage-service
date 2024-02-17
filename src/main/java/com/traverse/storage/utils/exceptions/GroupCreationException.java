package com.traverse.storage.utils.exceptions;

public class GroupCreationException extends RuntimeException {
    public GroupCreationException(final String message) {
        super(message);
    }
    public GroupCreationException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
