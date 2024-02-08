package com.traverse.storage.utils.exceptions.mongo;

public abstract class MongoDBException extends RuntimeException {
    public MongoDBException(final String message) {
        super(message);
    }
}
