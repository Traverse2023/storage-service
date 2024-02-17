package com.traverse.storage.utils.exceptions;

public abstract class NotFoundException extends StorageServiceException {
    public NotFoundException(final String message) {super(message);}
    public NotFoundException(final String message, final Throwable cause) {super(message, cause);}
}
