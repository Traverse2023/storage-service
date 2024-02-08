package com.traverse.storage.utils.exceptions.mongo;

public class MessageCreationException extends MongoDBException {
    public MessageCreationException(final String message) {
        super(message);
    }
}
