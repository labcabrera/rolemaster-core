package org.labcabrera.rolemaster.core.services.rmss.character.item;

import org.labcabrera.rolemaster.core.dto.AddSkill;
import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.CharacterInfo;
import org.labcabrera.rolemaster.core.services.character.CharacterAddSkillService;
import org.labcabrera.rolemaster.core.services.commons.character.processor.CharacterArmorPostProcessor;
import org.labcabrera.rolemaster.core.table.rmss.armor.ArmorModifier;
import org.labcabrera.rolemaster.core.table.rmss.armor.ArmorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
class ArmorCalculator {

	@Autowired
	private ArmorService armorService;

	@Autowired
	private CharacterArmorPostProcessor armorProcessor;

	@Autowired
	private CharacterAddSkillService addSkillService;

	public <E> Mono<E> apply(E data, CharacterInfo character) {
		int equippedArmor = character.getArmor().getArmor();
		ArmorModifier armorModifier = armorService.getArmorModifier(equippedArmor);
		String skillId = armorModifier.getSkillId();
		if (skillId != null && character.getSkill(skillId).isEmpty()) {
			return addSkillService.addSkill(character, AddSkill.builder().skillId(skillId).build())
				.thenReturn(character)
				.map(c -> {
					CharacterModificationContext ctx = CharacterModificationContext.builder().character(c).build();
					armorProcessor.accept(ctx);
					return c;
				})
				.thenReturn(data);
		}
		else {
			return Mono.just(character)
				.map(c -> {
					CharacterModificationContext ctx = CharacterModificationContext.builder().character(c).build();
					armorProcessor.accept(ctx);
					return c;
				})
				.thenReturn(data);
		}
	}

}
