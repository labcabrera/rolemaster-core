package org.labcabrera.rolemaster.core.model.tactical;

import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackMode;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TacticalCharacterSpecialAttack {

	@NotNull
	private AttackType type;

	@NotNull
	private MeleeAttackMode mode;

	@Nullable
	private String skillId;

	@Nullable
	private String weaponTable;

}
