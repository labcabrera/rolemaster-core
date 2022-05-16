package org.labcabrera.rolemaster.core.services.commons;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.model.HasMetadata;
import org.springframework.stereotype.Component;

@Component
public class MetadataModificationUpdater {

	public <E extends HasMetadata> E apply(E hasMetadata) {
		hasMetadata.getMetadata().setUpdated(LocalDateTime.now());
		return hasMetadata;
	}
}
