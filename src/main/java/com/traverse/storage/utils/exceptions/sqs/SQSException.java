package com.traverse.storage.utils.exceptions.sqs;

public abstract class SQSException extends Exception {
    public SQSException(String message) {
        super(message);
    }
}
