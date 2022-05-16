package org.labcabrera.rolemaster.core.model.character;

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
public class SpecialAttack {

	private String name;

	private String weaponTableId;

	private SpecialAttackSize size;

	private String skillId;

	private List<ItemFeature> features;

}
