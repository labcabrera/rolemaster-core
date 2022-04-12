package org.labcabrera.rolemaster.core.model.tactical.action;

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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction.ValidTacticalAction;
import org.labcabrera.rolemaster.core.validation.ValidationConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@ValidTacticalAction
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
	@Type(value = TacticalActionCustom.class, name = "custom"),
	@Type(value = TacticalActionMeleeAttack.class, name = "melee-attack"),
	@Type(value = TacticalActionMissileAttack.class, name = "missile-attack"),
	@Type(value = TacticalActionMovement.class, name = "movement"),
	@Type(value = TacticalActionMovingManeuver.class, name = "moving-maneuver"),
	@Type(value = TacticalActionSpellAttack.class, name = "spell-attack"),
	@Type(value = TacticalActionSpellCast.class, name = "spell-cast"),
	@Type(value = TacticalActionStaticManeuver.class, name = "static-maneuver")
})
@Document(collection = "tacticalActions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class TacticalAction {

	@Id
	private String id;

	private String roundId;

	@NotNull
	private String source;

	@NotNull
	private TacticalActionPhase priority;

	@NotNull
	@Min(1)
	@Max(100)
	private Integer actionPercent;

	private TacticalActionState state;

	private String notes;

	private Integer effectiveInitiative;

	@Builder.Default
	private Map<InitiativeModifier, Integer> initiativeModifiers = new LinkedHashMap<>();

	public Integer getInitiative() {
		return initiativeModifiers.values().stream().reduce(0, (a, b) -> a + b);
	}

	//	public <T> T as(Class<T extends TacticalAction> actionClass) {
	//		return (T) this;
	//	}

	@Target({ElementType.TYPE })
	@Retention(RetentionPolicy.RUNTIME)
	@Constraint(validatedBy = TacticalActionValidator.class)
	static @interface ValidTacticalAction {

		String message() default ValidationConstants.INVALID_TACTICAL_ACTION;

		Class<?>[] groups() default {};

	Class<? extends Payload>[] payload()default{};
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
}}
