package org.labcabrera.rolemaster.core.model.character;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectableSkill {

	private String key;

	private String description;

	private String group;

	private List<String> categories;

	private Integer ranks;

	private Integer skills;

}
