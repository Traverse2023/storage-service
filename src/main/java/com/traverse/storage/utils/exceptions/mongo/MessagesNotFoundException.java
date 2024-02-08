package com.traverse.storage.utils.exceptions.mongo;

public class MessagesNotFoundException extends MongoDBException {
    public MessagesNotFoundException(final String message) {
        super(message);
    }
}
