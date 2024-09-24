package com.chensoul.lss.web.validation;

public class UsernameExistsException extends RuntimeException {

    public UsernameExistsException(final String message) {
        super(message);
    }

}
