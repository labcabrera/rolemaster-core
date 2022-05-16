package org.labcabrera.rolemaster.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingPackageCategorySelection {

	private String key;

	private String categoryId;

	private Integer ranks;

}
