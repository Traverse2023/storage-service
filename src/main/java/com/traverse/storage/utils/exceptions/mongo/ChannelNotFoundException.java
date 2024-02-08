package com.traverse.storage.utils.exceptions.mongo;

public class ChannelNotFoundException extends MongoDBException {
    public ChannelNotFoundException(final String message) {
        super(message);
    }
}
