package org.labcabrera.rolemaster.core.model.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackMode;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.model.validation.ValidTacticalActionMeleAttack.TacticalActionMeleAttackValidator;

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
			result &= checkTargetCount(value, context);
			result &= checkSourceNotEqualsTarget(value, context);
			result &= checkRollNumber(value, context);
			return result;
		}

		private boolean checkTargetCount(TacticalActionMeleeAttack value, ConstraintValidatorContext context) {
			boolean result = true;
			switch (value.getMeleeAttackType()) {
			case FULL:
				if (value.getTargets().isEmpty()) {
					context.buildConstraintViolationWithTemplate(ValidationConstants.INVALID_ATTACK_MELEE_REQUIRED_TARGET)
						.addConstraintViolation();
					result = false;
				}
				break;
			case PRESS_AND_MELEE, REACT_AND_MELEE:
				int check = getExpectedTargets(value);
				if (value.getTargets().size() == check) {
					context.buildConstraintViolationWithTemplate(ValidationConstants.INVALID_ATTACK_MELEE_NOT_REQUIRED_TARGET)
						.addConstraintViolation();
					result = false;
				}
				break;
			default:
				break;
			}
			return result;
		}

		private boolean checkRollNumber(TacticalActionMeleeAttack value, ConstraintValidatorContext context) {
			int check = getExpectedTargets(value);
			if (value.getRolls().size() != check) {
				context.buildConstraintViolationWithTemplate(ValidationConstants.INVALID_ATTACK_MELEE_INVALID_ROLL_COUNT)
					.addConstraintViolation();
				return false;
			}
			return true;
		}

		private boolean checkSourceNotEqualsTarget(TacticalActionMeleeAttack value, ConstraintValidatorContext context) {
			for (String target : value.getTargets().values()) {
				if (target.equals(value.getSource())) {
					context.buildConstraintViolationWithTemplate(ValidationConstants.INVALID_ATTACK_SOURCE_EQUALS_TARGET)
						.addConstraintViolation();
					return false;
				}
			}
			return true;
		}

		private int getExpectedTargets(TacticalActionMeleeAttack value) {
			return value.getMeleeAttackMode() == MeleeAttackMode.MAIN_WEAPON ? 2 : 1;
		}

	}
}