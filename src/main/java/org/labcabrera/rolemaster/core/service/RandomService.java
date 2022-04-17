package org.labcabrera.rolemaster.core.service;

import java.security.SecureRandom;

import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.springframework.stereotype.Service;

@Service
public class RandomService {

	private SecureRandom random = new SecureRandom();

	public int dX(int value) {
		return 1 + random.nextInt(value);
	}

	public int d10() {
		return dX(10);
	}

	public int d100() {
		return dX(100);
	}

	public OpenRoll d100FullOpen() {
		int t0 = d100();
		int t1 = d100();
		int t2 = d100();
		int result = t0;
		OpenRoll roll = OpenRoll.builder().build();
		roll.getRolls().add(t0);
		if (t0 < 6) {
			roll.getRolls().add(t1);
			result -= t1;
			if (t1 > 95) {
				result -= t2;
				roll.getRolls().add(t2);
			}
		}
		else if (t0 > 95) {
			result += t1;
			roll.getRolls().add(t1);
			if (t1 > 95) {
				result += t2;
				roll.getRolls().add(t2);
			}

		}
		roll.setResult(result);
		return roll;
	}

}
