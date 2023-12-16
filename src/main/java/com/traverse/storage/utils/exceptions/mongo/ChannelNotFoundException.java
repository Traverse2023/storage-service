package com.traverse.storage.utils.exceptions.mongo;

public class ChannelNotFoundException extends MongoDBException {
    public ChannelNotFoundException(String message) {
        super(message);
    }
}
