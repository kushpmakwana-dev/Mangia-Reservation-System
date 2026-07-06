package com.kushPmakwana.mangia.Mangia.exceptions;

public class ResourcesNotFoundException extends RuntimeException {
    private final String entityName;
    public ResourcesNotFoundException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }

    public String getEntityName(){
        return entityName;
    }
}
