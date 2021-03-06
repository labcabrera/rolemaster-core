package org.labcabrera.rolemaster.core.model.exception;

@SuppressWarnings("serial")
public class MissingWeaponData extends RuntimeException {

	private static final String MSG_TEMPLATE = "Missing weapon %s data for %s - %s";

	public MissingWeaponData(String message) {
		super(message);
	}

	public MissingWeaponData(String weaponId, Integer roll, Integer armor) {
		super(String.format(MSG_TEMPLATE, weaponId, roll, armor));
	}

}
