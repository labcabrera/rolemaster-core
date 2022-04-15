package org.labcabrera.rolemaster.core.model.tactical;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "tacticalSessionLogs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TacticalSessionLog {

	@Id
	private String id;

	private String tacticalSessionId;

	private Integer round;

	private String message;

}
