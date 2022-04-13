package org.labcabrera.rolemaster.core.model.npc;

import java.util.ArrayList;
import java.util.List;

import org.labcabrera.rolemaster.core.model.tactical.action.MovementPace;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "npcs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Npc {

	@Id
	private String id;

	private String name;

	private Integer level;

	private String levelBaseModifier;

	private Integer hp;

	private String hpBaseModifier;

	@Builder.Default
	private List<NpcAttack> attacks = new ArrayList<>();

	private Integer armorType;

	private Integer defensiveBonus;

	private Boolean unique;

	private Integer baseMove;

	private Integer mmBonus;

	private NpcSpeed movementSpeed;

	private NpcSpeed attackQuickness;

	private MovementPace maxPace;

	private String universeId;

	private String npcNameGeneratorGroup;

}
