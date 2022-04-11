package org.labcabrera.rolemaster.core.model.tactical.actions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMeleeAttack.ValidTacticalActionMeleAttack;
import org.labcabrera.rolemaster.core.validation.ValidationConstants;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@ValidTacticalActionMeleAttack
@JsonPropertyOrder({ "type", "state", "priority", "actionPercent", "initiativeModifiers", "initiative", "source", "target",
	"meleAttackType", "parry", "notes" })
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TacticalActionMeleeAttack extends TacticalAction {

	@NotNull
	private MeleeAttackType meleeAttackType;

	private String target;

	private String shieldParryTarget;

	@Builder.Default
	private Integer parry = 0;

	private OpenRoll roll;

	@Builder.Default
	private Map<String, Integer> offensiveBonusMap = new LinkedHashMap<>();

	@Builder.Default
	private Map<String, Integer> defensiveBonusMap = new LinkedHashMap<>();

	private String weaponId;

	@Builder.Default
	private Boolean requiresCriticalResolution = false;

	public Integer getOffensiveBonus() {
		return offensiveBonusMap.values().stream().reduce(0, (a, b) -> a + b);
	}

	public Integer getDefensiveBonus() {
		return defensiveBonusMap.values().stream().reduce(0, (a, b) -> a + b);
	}

	@Target({ ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Constraint(validatedBy = TacticalActionMeleAttackValidator.class)
	static @interface ValidTacticalActionMeleAttack {

		String message() default ValidationConstants.INVALID_ATTACK_MELEE;

		Class<?>[] groups() default {};

		Class<? extends Payload>[] payload() default {};
	}

	private static class TacticalActionMeleAttackValidator
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
