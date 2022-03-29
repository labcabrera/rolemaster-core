package org.labcabrera.rolemaster.core.model.tactical.actions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMeleAttack.ValidTacticalActionMeleAttack;
import org.labcabrera.rolemaster.core.validation.ValidationConstants;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@ValidTacticalActionMeleAttack
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TacticalActionMeleAttack extends TacticalAction {

	@NotNull
	private MeleeAttackType meleAttackType;

	private String target;

	@Builder.Default
	private Integer parry = 0;

	@Target({ ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Constraint(validatedBy = TacticalActionMeleAttackValidator.class)
	static @interface ValidTacticalActionMeleAttack {

		String message() default ValidationConstants.INVALID_ATTACK_MELEE;

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};
	}

	private static class TacticalActionMeleAttackValidator
		implements ConstraintValidator<ValidTacticalActionMeleAttack, TacticalActionMeleAttack> {

		@Override
		public boolean isValid(TacticalActionMeleAttack value, ConstraintValidatorContext context) {
			boolean result = true;
			if (value.getMeleAttackType() != null) {
				switch (value.getMeleAttackType()) {
				case FULL:
					if (value.getTarget() == null) {
						context.buildConstraintViolationWithTemplate(ValidationConstants.INVALID_ATTACK_MELEE_REQUIRED_TARGET)
							.addConstraintViolation();
						result = false;
					}
					break;
				case PRESS_AND_MELEE:
				case REACT_AND_MELEE:
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
