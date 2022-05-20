package org.labcabrera.rolemaster.core.services.commons;

import java.util.List;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.RolemasterVersionService;
import org.labcabrera.rolemaster.core.model.exception.UnsupportedRolemasterVersionService;
import org.springframework.stereotype.Component;

@Component
public class VersionRouter {

	public <E extends RolemasterVersionService> E route(RolemasterVersion version, List<E> list) {
		return list.stream()
			.filter(e -> e.compatibleVersions().contains(version))
			.findFirst().orElseThrow(() -> new UnsupportedRolemasterVersionService(version));
	}

}
