package com.traverse.storage.utils.exceptions;

public class CreationException extends StorageServiceException {
    public CreationException(final String message, final Throwable cause) {
        super(message, cause);
    }
    public CreationException(final String message) {
        super(message);
    }
}
