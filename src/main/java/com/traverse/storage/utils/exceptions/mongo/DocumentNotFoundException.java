package com.traverse.storage.utils.exceptions.mongo;

public class DocumentNotFoundException extends MongoDBException {
    public DocumentNotFoundException(String message){
        super(message);
    }
}
