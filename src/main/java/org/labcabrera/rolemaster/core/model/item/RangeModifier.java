package org.labcabrera.rolemaster.core.model.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RangeModifier {

	private Integer range;

	private Integer modifier;

}
