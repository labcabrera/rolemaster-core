package org.labcabrera.rolemaster.core.model.tactical;

import java.util.Map;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.character.ContextCharacterModifiers;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Represents a certain state of a player or NPC (e.g. life points) associated with a tactical game session.")
@Document(collection = "tacticalCharacters")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TacticalCharacter {

	@Id
	private String id;

	private String tacticalSessionId;

	private String name;

	private String shortDescription;

	private String characterId;

	private Integer level;

	@Builder.Default
	private Boolean isNpc = false;

	@Builder.Default
	private Hp hp = new Hp();

	@Builder.Default
	private PowerPoints powerPoints = new PowerPoints();

	@Builder.Default
	private ExhaustionPoints exhaustionPoints = new ExhaustionPoints();

	@Builder.Default
	private CombatStatus combatStatus = new CombatStatus();

	@Builder.Default
	private ContextCharacterModifiers modifiers = new ContextCharacterModifiers();

	@Builder.Default
	private TacticalCharacterInventory inventory = new TacticalCharacterInventory();

	private Map<String, Integer> npcSkills;

	private Integer armor;

	private Integer defensiveBonus;

	@Builder.Default
	private EntityMetadata metadata = new EntityMetadata();

}
