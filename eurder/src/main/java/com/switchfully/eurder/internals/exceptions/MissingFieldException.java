package com.switchfully.eurder.internals.exceptions;

public class MissingFieldException extends RuntimeException{
    public MissingFieldException() {
    }

    public MissingFieldException(String message) {
        super(message);
    }

    public MissingFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingFieldException(Throwable cause) {
        super(cause);
    }
}
