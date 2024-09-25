package com.chensoul.lss.web.validation;

import com.chensoul.lss.web.model.User;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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
