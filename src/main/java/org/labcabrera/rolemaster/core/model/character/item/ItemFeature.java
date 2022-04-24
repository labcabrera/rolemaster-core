package org.labcabrera.rolemaster.core.model.character.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemFeature {

	private ItemFeatureType type;

	private String value;

}
