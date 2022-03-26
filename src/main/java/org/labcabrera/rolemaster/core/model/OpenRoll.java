package org.labcabrera.rolemaster.core.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenRoll {

	private int result;

	@Builder.Default
	private List<Integer> rolls = new ArrayList<>();

	public String toString() {
		if (rolls.size() == 1) {
			return String.valueOf(result);
		}
		return result + " " + rolls + "";
	}

}
