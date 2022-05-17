package org.labcabrera.rolemaster.core.model.exception;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;

@SuppressWarnings("serial")
public class UnsupportedRolemasterVersionService extends RuntimeException {

	public UnsupportedRolemasterVersionService(RolemasterVersion version) {
		super(String.format("Unsupported Rolemaster version " + version.getCode() + ".", version));
	}

	public UnsupportedRolemasterVersionService(String message) {
		super(message);
	}

}
