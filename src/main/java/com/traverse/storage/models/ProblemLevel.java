package com.traverse.storage.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.http.ParseException;

public enum ProblemLevel {
    HARD("HARD"),
    MEDIUM("MEDIUM"),
    EASY("EASY");

    final String text;

    ProblemLevel(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    @JsonCreator
    public static ProblemLevel fromString(String text) throws ParseException {
        for (ProblemLevel level: ProblemLevel.values()) {
            if(level.text.equalsIgnoreCase(text)) {
                return level;
            }
        }
        throw new ParseException("Unable to parse string into a valid Difficulty Level: ." + text);
    }
}