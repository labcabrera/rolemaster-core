package org.labcabrera.rolemaster.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Range<E extends Comparable<E>> {

	private E min;

	private E max;

}
