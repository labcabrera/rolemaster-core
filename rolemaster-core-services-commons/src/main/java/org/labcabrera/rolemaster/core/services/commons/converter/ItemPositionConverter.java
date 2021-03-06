package org.labcabrera.rolemaster.core.services.commons.converter;

import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.model.exception.BadRequestException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemPositionConverter implements Converter<String, ItemPosition> {

	@Override
	public ItemPosition convert(String code) {
		for (ItemPosition i : ItemPosition.values()) {
			if (i.getCode().equals(code)) {
				return i;
			}
		}
		throw new BadRequestException("Invalid item type " + code);
	}

}
