package org.labcabrera.rolemaster.core.model.tactical;

import java.util.HashMap;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.tactical.action.InitiativeModifier;
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

	private TacticalRoundState state;

	@Builder.Default
	private Boolean initiativeLoaded = false;

	@Builder.Default
	private Map<String, Integer> initiativeRollMap = new HashMap<>();

	@Builder.Default
	private Map<String, Integer> initiativeModifiersMap = new HashMap<>();

	@Builder.Default
	private Map<String, Map<InitiativeModifier, Integer>> initiatives = new HashMap<>();

	@Builder.Default
	private EntityMetadata metadata = new EntityMetadata();

}
