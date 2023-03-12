package com.backend.workflow.exception;


import com.backend.workflow.message.MessagesComponent;

public class NotAcceptableException extends RuntimeException{

    public NotAcceptableException() {
        super(MessagesComponent.get("search.NotAuthorized.exception.for.the.position"));
    }

}
