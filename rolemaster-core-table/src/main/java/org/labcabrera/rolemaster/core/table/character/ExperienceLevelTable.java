package org.labcabrera.rolemaster.core.table.character;

import org.springframework.stereotype.Component;

@Component
public class ExperienceLevelTable {

	public int getMaxLevel(long xp) {
		if (xp < 10000) {
			return 0;
		}
		else if (xp < 20000) {
			return 1;
		}
		else if (xp < 30000) {
			return 2;
		}
		else if (xp < 40000) {
			return 3;
		}
		else if (xp < 50000) {
			return 4;
		}
		else if (xp < 70000) {
			return 5;
		}
		else if (xp < 90000) {
			return 6;
		}
		else if (xp < 110000) {
			return 7;
		}
		else if (xp < 130000) {
			return 8;
		}
		else if (xp < 150000) {
			return 9;
		}
		else if (xp < 180000) {
			return 10;
		}
		else if (xp < 210000) {
			return 11;
		}
		else if (xp < 240000) {
			return 12;
		}
		else if (xp < 270000) {
			return 13;
		}
		else if (xp < 300000) {
			return 14;
		}
		else if (xp < 340000) {
			return 15;
		}
		else if (xp < 380000) {
			return 16;
		}
		else if (xp < 420000) {
			return 17;
		}
		else if (xp < 460000) {
			return 18;
		}
		else if (xp < 500000) {
			return 19;
		}
		long diference = xp - 500000;
		int x = (int) (diference / 50000);
		return 20 + x;
	}

	public Long getRequiredExperience(int level) {
		switch (level) {
		case 0:
			return 0L;
		case 1, 2, 3, 4, 5:
			return 10000L * level;
		case 6, 7, 8, 9, 10:
			return 50000L + ((level - 5) * 20000L);
		case 11, 12, 13, 14, 15:
			return 150000L + ((level - 10) * 30000L);
		case 16, 17, 18, 19, 20:
			return 300000L + ((level - 15) * 40000L);
		default:
			return 500000L + ((level - 20) * 50000L);
		}
	}

}
