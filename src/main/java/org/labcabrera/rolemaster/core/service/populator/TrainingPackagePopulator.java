package org.labcabrera.rolemaster.core.service.populator;

import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.model.character.TrainingPackage;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class TrainingPackagePopulator extends AbstractJsonPopulator<TrainingPackage> {

	@Override
	protected List<String> getResources() {
		return Arrays.asList(
			"data/populator/training/training-package-adventurer.json",
			"data/populator/training/training-package-assassin.json",
			"data/populator/training/training-package-crafter.json",
			"data/populator/training/training-package-explorer.json",
			"data/populator/training/training-package-highwayman.json",
			"data/populator/training/training-package-mercenary.json",
			"data/populator/training/training-packages.json",
			"data/populator/training/training-packages-character-law.json");
	}

	@Override
	protected TypeReference<List<TrainingPackage>> getTypeReference() {
		return new TypeReference<List<TrainingPackage>>() {
		};
	}
}
