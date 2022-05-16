package org.labcabrera.rolemaster.core.model.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
import org.labcabrera.rolemaster.core.model.validation.ValidTacticalAction.TacticalActionValidator;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TacticalActionValidator.class)
public @interface ValidTacticalAction {

	String message()

	default ValidationConstants.INVALID_TACTICAL_ACTION;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	static class TacticalActionValidator implements ConstraintValidator<ValidTacticalAction, TacticalAction> {

		@Override
		public boolean isValid(TacticalAction value, ConstraintValidatorContext context) {
			boolean result = true;
			switch (value.getPriority()) {
			case SNAP:
				if (value.getActionPercent() > 20) {
					context.buildConstraintViolationWithTemplate(ValidationConstants.INVALID_TACTICAL_ACTION_SNAP_PERCENT)
						.addConstraintViolation();
					result = false;
				}
				break;
			case NORMAL:
				if (value.getActionPercent() > 80) {
					context.buildConstraintViolationWithTemplate(ValidationConstants.INVALID_TACTICAL_ACTION_NORMAL_PERCENT)
						.addConstraintViolation();
					result = false;
				}
				break;
			case DELIBERATE:
			default:
				break;
			}
			return result;
		}
	}
}