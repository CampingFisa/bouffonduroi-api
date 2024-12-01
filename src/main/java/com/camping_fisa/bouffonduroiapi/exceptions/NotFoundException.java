package com.camping_fisa.bouffonduroiapi.exceptions;

import com.fasterxml.jackson.annotation.JsonAlias;

public class NotFoundException extends RuntimeException {

    private String id;
    private Class<?> objectClass;

    public NotFoundException(Integer id, Class<?> objectClass) {
        this.id = id == null ? "null" : String.valueOf(id);
        this.objectClass = objectClass;
    }
    public NotFoundException(String id, Class<?> objectClass) {
        this.id = id;
        this.objectClass = objectClass;
    }
    public NotFoundException(Class<?> objectClass) {
        this.objectClass = objectClass;
    }
    public NotFoundException(String message) {
        super(message);
    }
    @Override
    @JsonAlias("message")
    public String getMessage() {
        String objectName = (objectClass != null) ? objectClass.getSimpleName() : "Unknown";
        if (id == null) {
            return "Unable to retrieve instance of " + objectName + ".";
        }
        return "Unable to retrieve instance of " + objectName + " with id " + id + ".";
    }


}
