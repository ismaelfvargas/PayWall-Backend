package com.backend.workflow.message;

//A ResponseMessageé para a mensagem para o cliente que vamos usar no resto do controlador e manipulador de exceções.
public class ResponseMessage {
    private String message;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}