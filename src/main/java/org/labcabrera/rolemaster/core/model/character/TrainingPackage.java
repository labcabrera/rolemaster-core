package org.labcabrera.rolemaster.core.model.character;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("trainingPackages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrainingPackage {

	@Id
	private String id;

	private String name;

	private LifestyleTrainingType type;

	@Schema(description = "Time to adquire in months.")
	private Integer timeAcquisition;

	private Map<String, Integer> fixedSkillCategories;

	private Map<String, Integer> fixedSkills;

	private List<SelectableSkillCategory> selectableSkillCategoryList;

	private List<SelectableSkill> selectableSkillList;

	private List<AttributeType> statGains;

	private Integer statGainsSelection;

	private String startingMoney;

	@Builder.Default
	private Map<String, Integer> costByProfession = new LinkedHashMap<>();
}
