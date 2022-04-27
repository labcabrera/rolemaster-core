package org.labcabrera.rolemaster.core.service.context;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.item.Item;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;
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

	private TacticalCharacter source;

	@Builder.Default
	private Map<AttackTargetType, TacticalCharacter> targets = new EnumMap<>(AttackTargetType.class);

	@Builder.Default
	private Map<String, Item> itemMap = new HashMap<>();

	private List<TacticalAction> roundActions;

}
