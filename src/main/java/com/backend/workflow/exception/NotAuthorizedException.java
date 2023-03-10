package com.backend.workflow.exception;

import com.backend.workflow.message.MessagesComponent;

public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException() {
        super(MessagesComponent.get("search.NotAuthorized.exception"));
    }

    public NotAuthorizedException(String message) {
        super(MessagesComponent.get("search.NotAuthorized.exception.for.the.area", message));
    }
}
