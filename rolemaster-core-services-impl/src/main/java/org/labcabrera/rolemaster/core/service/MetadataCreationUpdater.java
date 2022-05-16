package org.labcabrera.rolemaster.core.service;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.model.HasMetadata;
import org.labcabrera.rolemaster.core.model.exception.DataConsistenceException;
import org.springframework.stereotype.Component;

@Component
public class MetadataCreationUpdater {

	public <E extends HasMetadata> E apply(E hasMetadata) {
		if (hasMetadata.getMetadata() == null) {
			throw new DataConsistenceException("Required medatadata in entity " + hasMetadata + ".");
		}
		hasMetadata.getMetadata().setCreated(LocalDateTime.now());
		return hasMetadata;
	}
}
