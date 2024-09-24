package com.chensoul.lss.web.validation;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

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
