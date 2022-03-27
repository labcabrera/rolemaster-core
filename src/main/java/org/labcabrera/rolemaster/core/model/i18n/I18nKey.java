package org.labcabrera.rolemaster.core.model.i18n;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class I18nKey {

	@Id
	private String id;

	private String description;

}
