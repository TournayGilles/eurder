package com.switchfully.eurder.internals.exceptions;

public class NoRightException extends RuntimeException {
    public NoRightException() {
    }

    public NoRightException(String message) {
        super(message);
    }

    public NoRightException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRightException(Throwable cause) {
        super(cause);
    }
}
