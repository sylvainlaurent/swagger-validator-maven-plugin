package com.github.sylvainlaurent.maven.swaggervalidator;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private final List<String> messages = new ArrayList<>();
    private boolean hasError;

    public boolean hasError() {
        return hasError;
    }

    public void encounteredError() {
        hasError = true;
    }

    public void addMessage(String msg) {
        this.messages.add(msg);
    }

    public void addMessages(List<String> msgs) {
        this.messages.addAll(msgs);
    }

    public List<String> getMessages() {
        return messages;
    }
}
