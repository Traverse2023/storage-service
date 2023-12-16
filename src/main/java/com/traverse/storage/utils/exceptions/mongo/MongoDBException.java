package com.traverse.storage.utils.exceptions.mongo;

public abstract class MongoDBException extends Exception {
    public MongoDBException(String message) {
        super(message);
    }
}
