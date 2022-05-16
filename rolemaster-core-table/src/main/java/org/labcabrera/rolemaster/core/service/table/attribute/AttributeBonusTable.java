package org.labcabrera.rolemaster.core.service.table.attribute;

import org.springframework.stereotype.Component;

@Component
public class AttributeBonusTable {

	public int getBonus(int value) {
		if (value > 101) {
			return 14;
		}
		else if (value > 100) {
			return 12;
		}
		else if (value > 99) {
			return 10;
		}
		else if (value > 97) {
			return 9;
		}
		else if (value > 95) {
			return 8;
		}
		else if (value > 93) {
			return 7;
		}
		else if (value > 91) {
			return 6;
		}
		else if (value > 89) {
			return 5;
		}
		else if (value > 84) {
			return 4;
		}
		else if (value > 79) {
			return 3;
		}
		else if (value > 74) {
			return 2;
		}
		else if (value > 69) {
			return 1;
		}
		else if (value > 30) {
			return 0;
		}
		else if (value > 25) {
			return -1;
		}
		else if (value > 20) {
			return -2;
		}
		else if (value > 15) {
			return -3;
		}
		else if (value > 10) {
			return -4;
		}
		else if (value > 9) {
			return -5;
		}
		else if (value > 7) {
			return -6;
		}
		else if (value > 5) {
			return -7;
		}
		else if (value > 3) {
			return -8;
		}
		else if (value > 1) {
			return -9;
		}
		else {
			return -10;
		}
	}

}
