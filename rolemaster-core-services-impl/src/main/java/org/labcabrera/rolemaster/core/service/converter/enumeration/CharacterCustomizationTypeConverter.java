package org.labcabrera.rolemaster.core.service.converter.enumeration;

import org.labcabrera.rolemaster.core.model.character.CharacterCustomizationType;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CharacterCustomizationTypeConverter implements Converter<String, CharacterCustomizationType> {

	@Override
	public CharacterCustomizationType convert(String code) {
		for (CharacterCustomizationType i : CharacterCustomizationType.values()) {
			if (i.getCode().equals(code)) {
				return i;
			}
		}
		throw new BadRequestException("Invalid item type " + code);
	}

}
