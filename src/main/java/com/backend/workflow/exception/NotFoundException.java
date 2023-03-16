package com.backend.workflow.exception;

import com.backend.workflow.message.MessagesComponent;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Not Found")
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super(MessagesComponent.get("search.NotFound.exception"));
    }

    public NotFoundException(String message) {
        super(message);
    }
}
