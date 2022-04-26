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

	@Builder.Default
	private List<String> raceWeywords = new ArrayList<>();

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
	private List<CharacterItem> items = new ArrayList<>();

	@Builder.Default
	private Map<String, Integer> skills = new LinkedHashMap<>();

	@Builder.Default
	private List<NpcSpecialAttack> specialAttacks = new ArrayList<>();

	private Integer baseMove;

	private Integer mmBonus;

	private NpcSpeed movementSpeed;

	private NpcSpeed attackQuickness;

	private MovementPace maxPace;

	@Builder.Default
	private NpcCriticalTable criticalTable = NpcCriticalTable.DEFAULT;

	private String universeId;

	private String npcNameGeneratorGroup;

	private List<String> notes;

}
