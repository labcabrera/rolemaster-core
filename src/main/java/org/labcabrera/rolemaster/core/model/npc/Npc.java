package org.labcabrera.rolemaster.core.model.npc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "npcs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Npc {

	@Id
	private String id;

	private String name;

	private Integer level;

	private Integer hp;

	private Boolean unique;

	@Builder.Default
	private List<String> universes = new ArrayList<>();

	@Builder.Default
	private List<NpcAttack> attacks = new ArrayList<>();

}
