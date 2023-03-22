package com.backend.workflow.exception;

import com.backend.workflow.message.MessagesComponent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegisteredUserException extends RuntimeException{

    public RegisteredUserException(String login ){
        super(MessagesComponent.get("search.RegisteredUserException") + " " + login);
    }
}
