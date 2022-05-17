package org.labcabrera.rolemaster.core.dto.user;

import org.labcabrera.rolemaster.core.model.RolemasterVersion;
import org.labcabrera.rolemaster.core.model.UnitSystem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPreferenceModification {

	private RolemasterVersion defaultVersion;

	private UnitSystem defaultUnitSystem;

	private String defaultUniverseId;

	private Double defaultBoardScale;
}
