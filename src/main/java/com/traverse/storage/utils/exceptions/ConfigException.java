package com.traverse.storage.utils.exceptions;

public class ConfigException extends StorageServiceException {
    public ConfigException(final String message){
        super(message);
    }
    public ConfigException(final String message, final Throwable cause){
        super(message, cause);
    }
}
