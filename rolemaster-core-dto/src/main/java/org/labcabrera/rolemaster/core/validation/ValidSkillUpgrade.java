package org.labcabrera.rolemaster.core.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.labcabrera.rolemaster.core.dto.SkillUpgrade;
import org.labcabrera.rolemaster.core.validation.ValidSkillUpgrade.SkillUpgradeValidator;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = SkillUpgradeValidator.class)
public @interface ValidSkillUpgrade {

	String message()

	default ValidationConstants.INVALID_SKILL_UPGRADE;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	static class SkillUpgradeValidator implements ConstraintValidator<ValidSkillUpgrade, SkillUpgrade> {

		@Override
		public boolean isValid(SkillUpgrade value, ConstraintValidatorContext context) {
			if (value.getCategoryRanks().isEmpty() && value.getSkillRanks().isEmpty()) {
				context.buildConstraintViolationWithTemplate(ValidationConstants.INVALID_SKILL_UPGRADE_REQUIRED_SKILLS)
					.addConstraintViolation();
				return false;
			}
			return true;
		}
	}
}