package org.labcabrera.rolemaster.core.model.character;

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

	private LifestyleTrainingType type;

	@Schema(description = "Time to adquire in months")
	private Integer timeAdquisition;

	private Map<String, Integer> skillCategoryRanks;

	private Map<String, Integer> skillRanks;

	private String startingMoney;
}