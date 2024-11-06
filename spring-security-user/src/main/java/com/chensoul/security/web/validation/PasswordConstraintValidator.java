package com.chensoul.security.web.validation;

import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.passay.DigitCharacterRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.SpecialCharacterRule;
import org.passay.UppercaseCharacterRule;
import org.passay.WhitespaceRule;

public class PasswordConstraintValidator implements ConstraintValidator<PasswordConstraint, String> {
  private PasswordConstraint passwordConstraint;

  @Override
  public void initialize(final PasswordConstraint passwordConstraint) {
    this.passwordConstraint = passwordConstraint;
  }

  @Override
  public boolean isValid(final String password, final ConstraintValidatorContext context) {
    final PasswordValidator validator = new PasswordValidator(Arrays.asList(new LengthRule(passwordConstraint.minLength(), passwordConstraint.maxLength()),
            new UppercaseCharacterRule(1), new DigitCharacterRule(1), new SpecialCharacterRule(1), new WhitespaceRule()));
    final RuleResult result = validator.validate(new PasswordData(password));
    if (result.isValid()) {
      return true;
    }
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate(String.join("\n", validator.getMessages(result))).addConstraintViolation();
    return false;
  }

}
