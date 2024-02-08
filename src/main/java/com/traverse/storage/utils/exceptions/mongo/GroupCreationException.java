package com.traverse.storage.utils.exceptions.mongo;

public class GroupCreationException extends MongoDBException {
    public GroupCreationException(final String message) {
        super(message);
    }
}
