package com.ll.hype.global.exception.custom;

public class EntityNotFoundException extends IllegalArgumentException {

    /**
     * 사용자에게 보여질 오류 메세지를 입력해 주세요.
     * @param message
     */
    public EntityNotFoundException(String message) {
        super(message);
    }
}
