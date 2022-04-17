package org.labcabrera.rolemaster.core.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.validation.ValidTacticalActionMeleAttack.TacticalActionMeleAttackValidator;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TacticalActionMeleAttackValidator.class)
public @interface ValidTacticalActionMeleAttack {

	String message() default ValidationConstants.INVALID_ATTACK_MELEE;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	static class TacticalActionMeleAttackValidator
		implements ConstraintValidator<ValidTacticalActionMeleAttack, TacticalActionMeleeAttack> {

		@Override
		public boolean isValid(TacticalActionMeleeAttack value, ConstraintValidatorContext context) {
			boolean result = true;
			if (value.getMeleeAttackType() != null) {
				switch (value.getMeleeAttackType()) {
				case FULL:
					if (value.getTarget() == null) {
						context.buildConstraintViolationWithTemplate(ValidationConstants.INVALID_ATTACK_MELEE_REQUIRED_TARGET)
							.addConstraintViolation();
						result = false;
					}
					break;
				case PRESS_AND_MELEE, REACT_AND_MELEE:
					if (value.getTarget() != null) {
						context.buildConstraintViolationWithTemplate(ValidationConstants.INVALID_ATTACK_MELEE_NOT_REQUIRED_TARGET)
							.addConstraintViolation();
						result = false;
					}
					break;
				default:
					break;
				}
			}
			return result;
		}
	}
}