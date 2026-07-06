package com.kushPmakwana.mangia.Mangia.exceptions;

public class UnmodifiableException extends RuntimeException {
    private final String entityName;
    public UnmodifiableException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }

    public String getEntityName(){
        return entityName;
    }

}
