package org.labcabrera.rolemaster.core.model.character;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document("lifestyleTrainingPackages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LifestyleTrainingPackage {

	@Id
	private String id;

	@Schema(description = "Time to adquire in months")
	private Integer timeAdquisition;

	private Map<String, Integer> skillCategoryRanks;

	private Map<String, Integer> skillRanks;

	//private List<> selectionCategorySkillRanks;

	//private List<Object> selectionSkillRanks;

	private String startingMoney;
}
