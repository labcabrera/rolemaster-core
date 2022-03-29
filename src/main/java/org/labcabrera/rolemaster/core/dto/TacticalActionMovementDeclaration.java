package org.labcabrera.rolemaster.core.dto;

import org.labcabrera.rolemaster.core.model.tactical.actions.MovementPace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class TacticalActionMovementDeclaration extends TacticalActionDeclaration {

	private MovementPace pace;

}
