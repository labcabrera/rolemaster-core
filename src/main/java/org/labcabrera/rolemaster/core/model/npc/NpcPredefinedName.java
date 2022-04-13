package org.labcabrera.rolemaster.core.model.npc;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "npcPredefinedNames")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NpcPredefinedName {

	@Id
	private String id;

	private String name;

	private String group;

}
