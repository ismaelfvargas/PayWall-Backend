package com.backend.workflow.exception;

import com.backend.workflow.message.MessagesComponent;

public class RoleException extends RuntimeException{

    public RoleException(String roles){
        super(MessagesComponent.get("search.RoleException"));
    }
}
