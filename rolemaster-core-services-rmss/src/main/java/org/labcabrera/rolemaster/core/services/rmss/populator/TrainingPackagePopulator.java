package org.labcabrera.rolemaster.core.services.rmss.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.character.TrainingPackage;
import org.labcabrera.rolemaster.core.services.commons.populator.AbstractJsonPopulator;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
class TrainingPackagePopulator extends AbstractJsonPopulator<TrainingPackage> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList(
			"data/populator/training/training-package-adventurer.json",
			"data/populator/training/training-package-amateur-mage.json",
			"data/populator/training/training-package-animal-friend.json",
			"data/populator/training/training-package-assassin.json",
			"data/populator/training/training-package-berserker.json",
			"data/populator/training/training-package-burglar.json",
			"data/populator/training/training-package-city-guard.json",
			"data/populator/training/training-package-cloistered-academic.json",
			"data/populator/training/training-package-con-man.json",
			"data/populator/training/training-package-crafter.json",
			"data/populator/training/training-package-crusading-academic.json",
			"data/populator/training/training-package-cut-purse.json",
			"data/populator/training/training-package-detective.json",
			"data/populator/training/training-package-diplomat.json",
			"data/populator/training/training-package-doctor.json",
			"data/populator/training/training-package-explorer.json",
			"data/populator/training/training-package-guardian.json",
			"data/populator/training/training-package-herbalist.json",
			"data/populator/training/training-package-highwayman.json",
			"data/populator/training/training-package-hunter.json",
			"data/populator/training/training-package-knight.json",
			"data/populator/training/training-package-loremaster.json",
			"data/populator/training/training-package-martial-artist.json",
			"data/populator/training/training-package-mercenary.json",
			"data/populator/training/training-package-merchant.json",
			"data/populator/training/training-package-performer.json",
			"data/populator/training/training-package-philosopher.json",
			"data/populator/training/training-package-sailor.json",
			"data/populator/training/training-package-scout.json",
			"data/populator/training/training-package-shaman-priest.json",
			"data/populator/training/training-package-soldier.json",
			"data/populator/training/training-package-spy.json",
			"data/populator/training/training-package-traveler.json",
			"data/populator/training/training-package-wanderer.json",
			"data/populator/training/training-package-weapon-master.json",
			"data/populator/training/training-package-zealot.json");
	}

	@Override
	protected TypeReference<List<TrainingPackage>> getTypeReference() {
		return new TypeReference<List<TrainingPackage>>() {
		};
	}
}
