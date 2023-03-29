package com.switchfully.eurder.internals.exceptions;

public class NoSuchOrderException extends RuntimeException {
    public NoSuchOrderException() {
    }

    public NoSuchOrderException(String message) {
        super(message);
    }

    public NoSuchOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchOrderException(Throwable cause) {
        super(cause);
    }
}
