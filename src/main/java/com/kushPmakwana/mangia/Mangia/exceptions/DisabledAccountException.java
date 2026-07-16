package com.kushPmakwana.mangia.Mangia.exceptions;

public class DisabledAccountException extends RuntimeException {
    public DisabledAccountException(String message) {
        super(message);
    }
}
