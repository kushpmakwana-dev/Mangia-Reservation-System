package com.kushPmakwana.mangia.Mangia.exceptions;

public class CustomException extends RuntimeException {
    private final String entityName;
    public CustomException(String entityName, String message) {
        super(message);
        this.entityName = entityName;
    }

    public String getEntityName(){
        return entityName;
    }
}
