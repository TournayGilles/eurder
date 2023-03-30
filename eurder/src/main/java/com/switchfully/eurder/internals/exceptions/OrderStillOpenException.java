package com.switchfully.eurder.internals.exceptions;

public class OrderStillOpenException extends RuntimeException {
    public OrderStillOpenException() {
    }

    public OrderStillOpenException(String message) {
        super(message);
    }

    public OrderStillOpenException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderStillOpenException(Throwable cause) {
        super(cause);
    }
}
