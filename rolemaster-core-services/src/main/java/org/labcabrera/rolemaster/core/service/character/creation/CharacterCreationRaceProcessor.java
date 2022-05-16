package org.labcabrera.rolemaster.core.service.character.creation;

import org.labcabrera.rolemaster.core.model.character.Race;
import org.labcabrera.rolemaster.core.model.character.creation.CharacterModificationContext;
import org.springframework.stereotype.Component;

import reactor.util.function.Tuple2;

@Component
public class CharacterCreationRaceProcessor {

	public CharacterModificationContext process(Tuple2<CharacterModificationContext, Race> tuple) {
		CharacterModificationContext ctx = tuple.getT1();
		Race race = tuple.getT2();
		ctx.setRace(race);
		ctx.getCharacter().setBodyDevelopmentProgression(race.getBodyDevelopmentProgression());
		ctx.getCharacter().setPowerPointProgression(race.getPowerPointsProgression().get(ctx.getCharacter().getRealm()));
		ctx.getCharacter().getDevelopmentPoints().setBackgroundOptions(race.getBackgroundOptions());
		ctx.getCharacter().getNotes().addAll(race.getSpecialAbilities());
		ctx.getCharacter().setSize(race.getSize());
		ctx.getCharacter().getArmor().setSkillAT(race.getSkinAT());
		return ctx;
	}
}
