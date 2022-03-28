package org.labcabrera.rolemaster.core.model.tactical;

import java.util.ArrayList;
import java.util.List;

import org.labcabrera.rolemaster.core.model.combat.Bleeding;
import org.labcabrera.rolemaster.core.model.combat.Penalty;
import org.springframework.data.annotation.Id;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Represents a certain state of a player or NPC (e.g. life points) associated with a tactical game session.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterTacticalContext {

	@Id
	private String id;

	private String tacticalSessionId;

	private String characterId;

	private String npcId;

	private String mainWeaponEquipedId;

	private String secondaryWeaponEquipedId;

	private String armorEquipedId;

	private Integer hp;

	@Builder.Default
	private Integer stunnedRounds = 0;

	@Builder.Default
	private Integer unconsciousRounds = 0;

	@Builder.Default
	private Integer canNotParryRounds = 0;

	private Integer mustParryRounds;

	@Builder.Default
	private List<Bleeding> bleding = new ArrayList<>();

	@Builder.Default
	private List<Penalty> penalty = new ArrayList<>();

}
