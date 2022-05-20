package org.labcabrera.rolemaster.core.model.character;

import org.labcabrera.rolemaster.core.model.conversion.FeetToMeterConverter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseMovementRate {

	@Schema(description = "Character base movement rate in feets.")
	private Double bmr;

	public Double bmrInMeters() {
		return FeetToMeterConverter.feetsToMeters(bmr, 1);
	}

}
