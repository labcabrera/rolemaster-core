package org.labcabrera.rolemaster.core.model.npc;

import java.util.List;

import org.labcabrera.rolemaster.core.model.character.item.ItemFeature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NpcSpecialAttack {

	private String name;

	private String weaponTableId;

	private String skillId;

	private List<ItemFeature> features;

}
