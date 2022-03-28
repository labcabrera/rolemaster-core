package org.labcabrera.rolemaster.core.model.tactical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalAction;
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
	private Map<String, Integer> initiativeMap = new HashMap<>();

	@Builder.Default
	private List<TacticalAction> actions = new ArrayList<>();

	@Builder.Default
	private EntityMetadata metadata = new EntityMetadata();

}
