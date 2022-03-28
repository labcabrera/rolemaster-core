package org.labcabrera.rolemaster.core.model.session;

import org.springframework.data.annotation.Id;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Represents a sequence of rounds in which a combat usually takes place.")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TacticalSession {

	@Id
	@Schema(description = "Tactical session identifier")
	private String id;

	@Schema(description = "Session identifier to which it belongs")
	private String sessionId;

	private TacticalSessionState state;

	@Builder.Default
	private Integer currentRound = 0;

}
