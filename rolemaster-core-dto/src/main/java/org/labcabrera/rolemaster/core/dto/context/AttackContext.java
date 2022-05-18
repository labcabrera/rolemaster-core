package org.labcabrera.rolemaster.core.dto.context;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.item.Item;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AttackContext extends TacticalContext implements HasItemMap {

	private TacticalActionAttack action;

	private CharacterInfo sourceCharacterInfo;

	@Builder.Default
	private Map<AttackTargetType, TacticalCharacter> targets = new EnumMap<>(AttackTargetType.class);

	@Builder.Default
	private Map<String, Item> itemMap = new HashMap<>();

	public TacticalCharacter getSource() {
		return this.characters.stream().filter(e -> e.getId().equals(action.getSource())).findFirst()
			.orElseThrow(() -> new DataConsistenceException(""));
	}

}
