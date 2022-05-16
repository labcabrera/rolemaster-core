package org.labcabrera.rolemaster.core.service.converter.enumeration;

import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.item.ItemType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ItemTypeConverter implements Converter<String, ItemType> {

	@Override
	public ItemType convert(String code) {
		for (ItemType i : ItemType.values()) {
			if (i.getCode().equals(code)) {
				return i;
			}
		}
		throw new BadRequestException("Invalid item type " + code);
	}

}
