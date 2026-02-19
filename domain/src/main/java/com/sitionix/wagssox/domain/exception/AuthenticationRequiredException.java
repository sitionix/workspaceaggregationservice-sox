package com.sitionix.wagssox.domain.exception;

public class AuthenticationRequiredException extends RuntimeException {

    public AuthenticationRequiredException(final String message) {
        super(message);
    }
}
