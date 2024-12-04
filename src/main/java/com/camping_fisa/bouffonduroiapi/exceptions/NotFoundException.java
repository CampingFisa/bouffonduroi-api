package com.camping_fisa.bouffonduroiapi.exceptions;

import com.camping_fisa.bouffonduroiapi.entities.questions.Theme;
import com.fasterxml.jackson.annotation.JsonAlias;

public class NotFoundException extends RuntimeException {

    private String id;
    private Class<?> objectClass;

    public NotFoundException(String id, Class<?> objectClass) {
        super("Unable to retrieve instance of " + (objectClass != null ? objectClass.getSimpleName() : "Unknown object") + " with id " + id + ".");
        this.id = id;
        this.objectClass = objectClass;
    }

    public NotFoundException(Class<?> objectClass) {
        super("Unable to retrieve instance of " + (objectClass != null ? objectClass.getSimpleName() : "Unknown object") + ".");
        this.objectClass = objectClass;
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(int toIntExact, Class<Theme> objectClass) {
    }

    @Override
    @JsonAlias("message")
    public String getMessage() {
        String objectName = (objectClass != null) ? objectClass.getSimpleName() : "Unknown object";
        if (id == null) {
            return "Unable to retrieve instance of " + objectName + ".";
        }
        return "Unable to retrieve instance of " + objectName + " with id " + id + ".";
    }
}
