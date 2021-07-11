package com.epam.esm.exception;

public class PageNotFoundException extends RuntimeException{
    private final int code;

    public PageNotFoundException(String message, int code) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
