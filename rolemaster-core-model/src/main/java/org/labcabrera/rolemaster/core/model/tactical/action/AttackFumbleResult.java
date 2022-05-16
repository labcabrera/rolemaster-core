package org.labcabrera.rolemaster.core.model.tactical.action;

import org.labcabrera.rolemaster.core.model.combat.FumbleTableResult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttackFumbleResult {

	private Integer weaponFumble;

	private FumbleType type;

	private Integer roll;

	private FumbleTableResult result;

}
