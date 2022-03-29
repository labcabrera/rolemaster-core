package org.labcabrera.rolemaster.core.model.tactical.actions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction.ValidTacticalAction;
import org.labcabrera.rolemaster.core.validation.ValidationConstants;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@ValidTacticalAction
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class TacticalAction {

	@Id
	private String id;

	@NotNull
	private String source;

	@NotNull
	private TacticalActionPhase priority;

	@NotNull
	@Min(1)
	@Max(100)
	private Integer actionPercent;

	private TacticalActionState state;

	@Target({ ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Constraint(validatedBy = TacticalActionValidator.class)
	static @interface ValidTacticalAction {

		String message() default ValidationConstants.INVALID_TACTICAL_ACTION;

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};
	}

	private static class TacticalActionValidator implements ConstraintValidator<ValidTacticalAction, TacticalAction> {

		@Override
		public boolean isValid(TacticalAction value, ConstraintValidatorContext context) {
			boolean result = true;
			if (value.getPriority() != null && value.getActionPercent() != null) {
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
			}
			return result;
		}
	}
}
