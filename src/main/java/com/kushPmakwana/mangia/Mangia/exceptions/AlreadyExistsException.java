package com.kushPmakwana.mangia.Mangia.exceptions;

public class AlreadyExistsException extends RuntimeException {
    private final String entityName;

    public AlreadyExistsException(String entityName, String message) {
        super(message);
        this.entityName = entityName;
    }

    public String getEntityName(){
        return entityName;
    }
}
