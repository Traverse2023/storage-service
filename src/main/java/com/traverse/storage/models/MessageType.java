package com.traverse.storage.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.http.ParseException;

public enum MessageType {

    GROUP_MESSAGE("GROUP_MESSAGE"),
    ADMIN_MESSAGE("ADMIN_MESSAGE"),
    DIRECT_MESSAGE("DIRECT_MESSAGE");

    final String text;

    MessageType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    @JsonCreator
    public static MessageType fromString(String text) throws ParseException {
        for (MessageType type: MessageType.values()) {
            if(type.text.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new ParseException("Unable to parse string into a valid NotificationType: ." + text);
    }
}
