package org.labcabrera.rolemaster.core.model.tactical;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TacticalNpcInstance {

	@Id
	private String id;

	private String npcId;

	private String name;

	private EntityMetadata metadata;

}
