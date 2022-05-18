package org.labcabrera.rolemaster.core.services.commons.converter;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RolemasterVersionConverter implements Converter<String, RolemasterVersion> {

	@Override
	public RolemasterVersion convert(String code) {
		for (RolemasterVersion i : RolemasterVersion.values()) {
			if (i.getCode().equals(code)) {
				return i;
			}
		}
		throw new BadRequestException("Invalid Rolemaster version " + code + ".");
	}

}
