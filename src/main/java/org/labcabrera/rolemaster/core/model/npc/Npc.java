package org.labcabrera.rolemaster.core.model.npc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
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

	private String shortDescription;

	private boolean unique;

	private Integer level;

	private String levelBaseModifier;

	private Integer hp;

	private Integer powerPoints;

	private String hpBaseModifier;

	private Integer armorType;

	private Integer defensiveBonus;

	@Builder.Default
	private List<NpcAttack> attacks = new ArrayList<>();

	@Builder.Default
	private List<CharacterItem> items = new ArrayList<>();

	@Builder.Default
	private Map<String, Integer> skills = new LinkedHashMap<>();

	private Integer baseMove;

	private Integer mmBonus;

	private NpcSpeed movementSpeed;

	private NpcSpeed attackQuickness;

	private MovementPace maxPace;

	private String universeId;

	private String npcNameGeneratorGroup;

}
