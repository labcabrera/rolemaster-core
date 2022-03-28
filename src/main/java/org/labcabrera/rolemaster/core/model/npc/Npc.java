package org.labcabrera.rolemaster.core.model.npc;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "npnDefinitions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Npc {

	@Id
	private String id;

	private String name;

	private Integer hp;

	private List<NpcAttack> attacks;

}
