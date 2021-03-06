package org.labcabrera.rolemaster.core.model.i18n;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Language {

	@Id
	private String id;

	private String name;

}
