package com.traverse.storage.utils.exceptions;

import lombok.*;
import org.apache.http.HttpStatus;

import java.time.ZonedDateTime;


public abstract class StorageServiceException extends RuntimeException {
    public StorageServiceException(final String message) {super(message);}
    public StorageServiceException(final String message, final Throwable cause) {super(message, cause);}
}
