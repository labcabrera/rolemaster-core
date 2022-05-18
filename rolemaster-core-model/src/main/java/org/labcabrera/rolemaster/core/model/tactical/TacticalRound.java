package org.labcabrera.rolemaster.core.model.tactical;

import java.util.LinkedHashMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "tacticalRounds")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TacticalRound {

	@Id
	private String id;

	private String tacticalSessionId;

	private Integer round;

	@Builder.Default
	private Map<String, TacticalRoundCharacterInitiative> initiatives = new LinkedHashMap<>();

	@Builder.Default
	private EntityMetadata metadata = new EntityMetadata();

}
