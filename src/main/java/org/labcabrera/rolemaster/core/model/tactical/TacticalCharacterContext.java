package org.labcabrera.rolemaster.core.model.tactical;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.character.ContextCharacterModifiers;
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
public class TacticalCharacterContext {

	@Id
	private String id;

	private String tacticalSessionId;

	private String name;

	private String characterId;

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
	private ContextCharacterItems items = new ContextCharacterItems();

	@Builder.Default
	private EntityMetadata metadata = new EntityMetadata();

}
