package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;
import org.labcabrera.rolemaster.core.table.weapon.WeaponTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MeleeAttackServiceWeaponTableProcessor implements UnaryOperator<MeleeAttackContext> {

	private static final String PATTERN_HP = "^([0-9]+)$";
	private static final String PATTERN_CRIT = "^([0-9]+)(\\w)(\\w)$";

	@Autowired
	private WeaponTable weaponTable;

	public MeleeAttackContext apply(MeleeAttackContext context) {
		String weaponId = context.getAction().getWeaponId();
		Integer targetArmor = context.getTarget().getArmorType();

		int offensiveBonus = context.getAction().getOffensiveBonus();
		int defensiveBonus = context.getAction().getDefensiveBonus();
		int attackRoll = offensiveBonus - defensiveBonus + context.getAction().getRoll().getResult();

		if (attackRoll > 150) {
			attackRoll = 150;
		}
		else if (attackRoll < 1) {
			attackRoll = 1;
		}

		String stringResult = weaponTable.get(weaponId, targetArmor, attackRoll);

		Integer hp = 0;
		CriticalSeverity criticalSeverity = null;
		CriticalType criticalType = null;

		Pattern patternHp = Pattern.compile(PATTERN_HP);
		Matcher matcherHp = patternHp.matcher(stringResult);
		if (matcherHp.matches()) {
			hp = Integer.parseInt(matcherHp.group(1));
		}
		else {
			Pattern patternCrit = Pattern.compile(PATTERN_CRIT);
			Matcher matcherCrit = patternCrit.matcher(stringResult);
			if (matcherCrit.matches()) {
				hp = Integer.parseInt(matcherCrit.group(1));
				criticalSeverity = CriticalSeverity.valueOf(matcherCrit.group(2));
				criticalType = CriticalType.valueOf(matcherCrit.group(3));
			}
			else {
				throw new RuntimeException("Invalid result");
			}
		}

		context.setHp(hp);
		context.setCriticalSeverity(criticalSeverity);
		context.setCriticalType(criticalType);
		return context;
	}

}
