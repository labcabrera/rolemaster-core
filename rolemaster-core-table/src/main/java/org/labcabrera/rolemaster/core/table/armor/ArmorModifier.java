package org.labcabrera.rolemaster.core.table.armor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArmorModifier {

	private Integer armor;

	private String skillId;

	private Integer mmMin;

	private Integer mmMax;

	private Integer missilePenalty;

	private Integer quicknessPenalty;

	private Boolean includeGreaves;

}
