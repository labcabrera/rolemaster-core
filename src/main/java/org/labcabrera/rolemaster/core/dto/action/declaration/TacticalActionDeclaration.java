package org.labcabrera.rolemaster.core.dto.action.declaration;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.model.tactical.TacticalActionPhase;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
	@Type(value = TacticalActionMovementDeclaration.class, name = "movement"),
	@Type(value = TacticalActionMeleeAttackDeclaration.class, name = "melee-attack")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class TacticalActionDeclaration {

	@NotNull
	private String source;

	@NotNull
	private TacticalActionPhase priority;

	@NotNull
	@Min(1)
	@Max(100)
	private Integer actionPercent;

	private String notes;

}
