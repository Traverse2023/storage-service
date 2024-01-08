package com.traverse.storage.models;

import org.apache.http.ParseException;

public enum NotificationType {
    FriendRequest("friendRequest"),
    ChannelMessage("channelMessage"),
    AdminMessage("adminMessage"),
    DirectMessage("directMessage");

    private final String text;

    NotificationType(String text) {
        this.text = text;
    }

    public String toString() {
        return this.text;
    }

    public static NotificationType fromString(String text) throws ParseException {
        for (NotificationType type: NotificationType.values()) {
            if(type.text.equalsIgnoreCase(text)) {
                return type;
            }
        }
        throw new ParseException("Unable to parse string {} into a valid NotificationType.");
    }
}
