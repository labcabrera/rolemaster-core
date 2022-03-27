package org.labcabrera.rolemaster.core.table.character;

import org.springframework.stereotype.Component;

@Component
public class StrideChartTable {

	public Integer getValue(Integer value) {
		if (value > 237) {
			return 20;
		}
		else if (value > 223) {
			return 15;
		}
		else if (value > 209) {
			return 10;
		}
		else if (value > 195) {
			return 5;
		}
		else if (value > 181) {
			return 0;
		}
		else if (value > 167) {
			return -5;
		}
		else if (value > 153) {
			return -10;
		}
		else if (value > 139) {
			return -15;
		}
		else if (value > 125) {
			return -20;
		}
		else if (value > 111) {
			return -25;
		}
		else if (value > 97) {
			return -30;
		}
		else if (value > 83) {
			return -35;
		}
		return -40;
	}

}
