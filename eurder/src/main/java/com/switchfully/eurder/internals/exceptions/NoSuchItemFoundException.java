package com.switchfully.eurder.internals.exceptions;

public class NoSuchItemFoundException extends RuntimeException {
    public NoSuchItemFoundException() {
    }

    public NoSuchItemFoundException(String message) {
        super(message);
    }

    public NoSuchItemFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchItemFoundException(Throwable cause) {
        super(cause);
    }
}
