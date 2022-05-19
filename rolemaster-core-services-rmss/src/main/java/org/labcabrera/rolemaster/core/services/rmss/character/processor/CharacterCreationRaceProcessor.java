package org.labcabrera.rolemaster.core.services.rmss.character.processor;

import org.labcabrera.rolemaster.core.dto.context.CharacterModificationContext;
import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.services.character.CharacterUpdatePostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(CharacterUpdatePostProcessor.Orders.RACE)
class CharacterCreationRaceProcessor implements CharacterUpdatePostProcessor {

	@Override
	public void accept(CharacterModificationContext ctx) {
		Race race = ctx.getRace();
		ctx.getCharacter().setBodyDevelopmentProgression(race.getBodyDevelopmentProgression());
		ctx.getCharacter().setPowerPointProgression(race.getPowerPointsProgression().get(ctx.getCharacter().getRealm()));
		ctx.getCharacter().getDevelopmentPoints().setBackgroundOptions(race.getBackgroundOptions());
		ctx.getCharacter().getNotes().addAll(race.getSpecialAbilities());
		ctx.getCharacter().setSize(race.getSize());
		ctx.getCharacter().getArmor().setSkillAT(race.getSkinAT());
	}
}
