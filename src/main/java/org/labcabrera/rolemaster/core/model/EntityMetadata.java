package org.labcabrera.rolemaster.core.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntityMetadata {

	private LocalDateTime created;

	private LocalDateTime updated;

}
