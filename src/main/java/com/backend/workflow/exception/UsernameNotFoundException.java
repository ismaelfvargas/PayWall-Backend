package com.backend.workflow.exception;


import com.backend.workflow.message.MessagesComponent;


public class UsernameNotFoundException extends RuntimeException{

    public UsernameNotFoundException() {
        super(MessagesComponent.get("search.UsernameNotFoundException"));
    }
}
