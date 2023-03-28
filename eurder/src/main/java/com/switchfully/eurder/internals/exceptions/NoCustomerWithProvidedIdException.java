package com.switchfully.eurder.internals.exceptions;

public class NoCustomerWithProvidedIdException extends RuntimeException {
    public NoCustomerWithProvidedIdException() {
    }

    public NoCustomerWithProvidedIdException(String message) {
        super(message);
    }

    public NoCustomerWithProvidedIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCustomerWithProvidedIdException(Throwable cause) {
        super(cause);
    }
}
