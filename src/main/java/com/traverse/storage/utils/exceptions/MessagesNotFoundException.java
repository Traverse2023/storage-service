package com.traverse.storage.utils.exceptions;

public class MessagesNotFoundException extends NotFoundException {
    public MessagesNotFoundException(final String message) {
        super(message);
    }
    public MessagesNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
