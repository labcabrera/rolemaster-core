package org.labcabrera.rolemaster.core.model.npc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NpcAttack {

	private String name;

	private Integer bonus;

	private String type;

}
