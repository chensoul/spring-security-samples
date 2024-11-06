package com.chensoul.security.web.validation;

public class UsernameExistsException extends RuntimeException {

  public UsernameExistsException(final String message) {
    super(message);
  }

}
