package org.labcabrera.rolemaster.core.model.character.status;

import java.util.ArrayList;
import java.util.List;

import org.labcabrera.rolemaster.core.model.combat.Bleeding;
import org.labcabrera.rolemaster.core.model.combat.Penalty;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacterStatus {

	@Id
	private String id;

	private String characterId;

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
