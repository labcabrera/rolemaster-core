package org.labcabrera.rolemaster.core.model;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntityMetadata {

	@Schema(description = "Entity creation date.", required = true)
	private LocalDateTime created;

	@Schema(description = "Entity modification date.", required = false)
	private LocalDateTime updated;

}
