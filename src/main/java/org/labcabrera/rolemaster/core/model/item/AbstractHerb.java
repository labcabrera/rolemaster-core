package org.labcabrera.rolemaster.core.model.item;

import org.labcabrera.rolemaster.core.model.maneuver.ManeuverDificulty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractHerb extends Item {

	private HerbForm herbForm;

	private HerbPreparation preparation;

	private HerbClimateCode climateCode;

	private String localeCode;

	private String location;

	private ManeuverDificulty findingDificulty;

	private Integer level;

	@Builder.Default
	private Integer addictionFactor = 0;

}
