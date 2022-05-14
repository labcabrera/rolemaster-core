package org.labcabrera.rolemaster.core.dto.action.execution;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "type")
@JsonSubTypes({
	@Type(value = MovementExecution.class, name = "movement"),
	@Type(value = MeleeAttackExecution.class, name = "melee-attack"),
	@Type(value = MissileAttackExecution.class, name = "missile-attack"),
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class TacticalActionExecution {

	private String notes;
}
