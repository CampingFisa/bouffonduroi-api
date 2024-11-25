package com.camping_fisa.bouffonduroiapi.exceptions;

import com.fasterxml.jackson.annotation.JsonAlias;

public class NotFoundException extends RuntimeException {

    private String id;
    private Class<?> objectClass;

    public NotFoundException(Integer id, Class<?> objectClass) {
        this.id = id == null ? "null" : String.valueOf(id);
        this.objectClass = objectClass;
    }

    public NotFoundException(Class<?> objectClass) {
        this.objectClass = objectClass;
    }
    public NotFoundException(String message) {
        super(message);
    }
    @JsonAlias("message")
    public String getMessage() {
        if (id == null) {
            return "Unable to retrieve instance of " + objectClass.getSimpleName() + ".";
        }
        return "Unable to retrieve instance of " + objectClass.getSimpleName() + " with id " + id + ".";
    }
}
