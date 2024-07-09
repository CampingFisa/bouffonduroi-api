package com.camping_fisa.bouffonduroiapi.exceptions;

import com.fasterxml.jackson.annotation.JsonAlias;

public class BusinessException extends RuntimeException {

    private Class<?> objectClass;

    private String message;

    public BusinessException(Class<?> objectClass, String message) {
        this.objectClass = objectClass;
        this.message = message;
    }


    @JsonAlias("message")
    public String getMessage() {
        if(message.isEmpty()){
            return "Given information concerning " + objectClass.getSimpleName() + " is not valid.";
        }
        return "Given information concerning " + objectClass.getSimpleName() + " is not valid because " + message;
    }
}
