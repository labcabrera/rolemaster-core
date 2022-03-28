package org.labcabrera.rolemaster.core.model.combat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Penalty {

	private int penalty;

	private Integer rounds;

}
