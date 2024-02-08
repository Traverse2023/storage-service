package com.traverse.storage.utils.exceptions.mongo;

public class GroupNotFoundException extends MongoDBException {
    public GroupNotFoundException(final String message){
        super(message);
    }
}
