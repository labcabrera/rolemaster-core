package org.labcabrera.rolemaster.core.model.tactical;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hp {

	private Integer max;

	private Integer current;

	public Integer getPercent() {
		return (100 * current) / max;
	}

	public void add(int value) {
		this.current += value;
	}

	public void subtract(Integer value) {
		this.current -= value;
	}
}
