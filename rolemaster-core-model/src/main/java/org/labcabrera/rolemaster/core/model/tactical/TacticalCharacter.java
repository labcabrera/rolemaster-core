package org.labcabrera.rolemaster.core.model.tactical;

import java.util.ArrayList;
import java.util.List;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.HasAuthorization;
import org.labcabrera.rolemaster.core.model.character.BaseMovementRate;
import org.labcabrera.rolemaster.core.model.character.ContextCharacterModifiers;
import org.labcabrera.rolemaster.core.model.character.SpecialAttack;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
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
public class TacticalCharacter implements HasAuthorization {

	@Id
	private String id;

	private String tacticalSessionId;

	private String name;

	private String shortDescription;

	private String characterId;

	private Integer level;

	private boolean isNpc;

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
	private List<CharacterItem> items = new ArrayList<>();

	@Builder.Default
	private List<SpecialAttack> specialAttacks = new ArrayList<>();

	private Integer armor;

	private Integer defensiveBonus;

	private BaseMovementRate baseMovementRate;

	private Integer mmBonus;

	@Builder.Default
	private TacticalCharacterState state = TacticalCharacterState.NORMAL;

	private String owner;

	@Builder.Default
	private EntityMetadata metadata = new EntityMetadata();

}
