package org.labcabrera.rolemaster.core.dto.actions;

import org.labcabrera.rolemaster.core.model.tactical.actions.MovementPace;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@JsonPropertyOrder({ "priority", "actionPercent", "source", "pace", "notes" })
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TacticalActionMovementDeclaration extends TacticalActionDeclaration {

	private MovementPace pace;

}
