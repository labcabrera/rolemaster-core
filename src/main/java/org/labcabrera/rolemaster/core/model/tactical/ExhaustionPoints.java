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
}
