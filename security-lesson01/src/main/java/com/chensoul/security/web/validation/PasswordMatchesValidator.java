package com.chensoul.security.web.validation;

import com.chensoul.security.web.model.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

  @Override
  public void initialize(final PasswordMatches passwordMatches) {
  }

  @Override
  public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
    final User user = (User) obj;
    return user.getPassword().equals(user.getPasswordConfirmation());
  }
}
