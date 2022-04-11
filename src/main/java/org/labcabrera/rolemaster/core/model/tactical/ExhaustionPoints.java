package org.labcabrera.rolemaster.core.model.tactical;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExhaustionPoints {

	private BigDecimal max;

	private BigDecimal current;

	public Integer getPercent() {
		return current.multiply(new BigDecimal(100)).divide(max).intValue();
	}

	public void substract(BigDecimal exhaustionPoints) {
		this.current = this.current.subtract(exhaustionPoints);
	}
}
