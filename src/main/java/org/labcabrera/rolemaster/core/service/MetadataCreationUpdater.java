package org.labcabrera.rolemaster.core.service;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.model.HasMetadata;
import org.springframework.stereotype.Component;

@Component
public class MetadataCreationUpdater {

	public <E extends HasMetadata> E apply(E hasMetadata) {
		hasMetadata.getMetadata().setCreated(LocalDateTime.now());
		return hasMetadata;
	}
}
