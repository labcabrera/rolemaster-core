package org.labcabrera.rolemaster.core.service.populator;

import java.util.List;

import org.labcabrera.rolemaster.core.model.character.LifestyleTrainingPackage;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component
public class LifestyleTrainingPackagePopulator extends AbstractJsonPopulator<LifestyleTrainingPackage> {

	@Override
	protected String getResource() {
		return "data/populator/lifestyle-training-packages.json";
	}

	@Override
	protected TypeReference<List<LifestyleTrainingPackage>> getTypeReference() {
		return new TypeReference<List<LifestyleTrainingPackage>>() {
		};
	}
}
