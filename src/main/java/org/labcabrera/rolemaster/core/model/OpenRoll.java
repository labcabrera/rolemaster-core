package org.labcabrera.rolemaster.core.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.labcabrera.rolemaster.core.exception.BadRequestException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenRoll {

	public static OpenRoll of(Integer... values) {
		if (values.length < 1) {
			throw new BadRequestException("Missing roll");
		}
		List<Integer> list = Arrays.asList(values);
		Integer result = list.stream().reduce(0, (a, b) -> a + b);
		return OpenRoll.builder()
			.result(result)
			.rolls(list)
			.build();
	}

	private int result;

	@Builder.Default
	private List<Integer> rolls = new ArrayList<>();

	public Integer getFirstRoll() {
		return rolls.iterator().next();
	}

	public String toString() {
		if (rolls.size() == 1) {
			return String.valueOf(result);
		}
		return result + " " + rolls + "";
	}

}
