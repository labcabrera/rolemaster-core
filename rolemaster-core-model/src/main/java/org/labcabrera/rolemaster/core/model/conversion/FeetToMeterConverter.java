package org.labcabrera.rolemaster.core.model.conversion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FeetToMeterConverter {

	private static final BigDecimal METER_CONVERSION = new BigDecimal(3.281);

	public static Double feetsToMeters(Double value, int scale) {
		return value == null ? 0 : new BigDecimal(value).divide(METER_CONVERSION, scale, RoundingMode.HALF_EVEN).doubleValue();
	}

}
