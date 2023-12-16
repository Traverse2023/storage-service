package com.traverse.storage.utils.exceptions.mongo;

public class MessagesNotFoundException extends MongoDBException {
    public MessagesNotFoundException(String message) {
        super(message);
    }
}
