package com.ll.hype.global.exception.custom;

public class EntityNotFoundException extends IllegalArgumentException{
    public EntityNotFoundException() {
        super();
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
