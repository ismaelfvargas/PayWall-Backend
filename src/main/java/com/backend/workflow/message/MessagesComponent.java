package com.backend.workflow.message;

import com.backend.workflow.config.MessagesConfiguration;
import org.springframework.stereotype.Component;

@Component
public class MessagesComponent {

    private static MessagesConfiguration messages;

    private MessagesComponent() {}

    public static void setMessages(MessagesConfiguration messages) {
        MessagesComponent.messages = messages;
    }

    public static String get(String code) {
        return messages.get(code);
    }

    public static String get(String code, String... parameters) {
        return messages.get(code, parameters);
    }
}
