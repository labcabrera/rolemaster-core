package org.labcabrera.rolemaster.core.model.combat;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Deprecated
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Attack {

	@Id
	private String id;

	@NotNull
	private String tacticalSession;

	@NotNull
	private String attackerId;

	@NotNull
	private String defenderId;

	private List<AttackModifier> attackModifiers;

	private List<AttackModifier> defenseModifiers;

	private AttackState state;

	private OpenRoll openRoll;

	private EntityMetadata metadata;

}
