package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalCriticalResult;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.TacticalAttackContext;
import org.labcabrera.rolemaster.core.table.weapon.WeaponTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MeleeAttackServiceWeaponTableProcessor implements UnaryOperator<TacticalAttackContext> {

	private static final String PATTERN_HP = "^([0-9]+)$";
	private static final String PATTERN_CRIT = "^([0-9]+)(\\w)(\\w)$";

	@Autowired
	private WeaponTable weaponTable;

	public TacticalAttackContext apply(TacticalAttackContext context) {
		if (context.getAction().getFumbleResult() != null) {
			return context;
		}
		String weaponId = context.getSource().getItems().getMainWeaponId();
		int targetArmor = context.getTarget().getArmorType();

		int offensiveBonus = context.getAction().getOffensiveBonus();
		int defensiveBonus = context.getAction().getDefensiveBonus();
		int roll = context.getExecution().getRoll().getResult();

		//TODO Check pifia / rotura

		int attackResult = Integer.min(150, Integer.max(1, offensiveBonus - defensiveBonus + roll));

		String stringResult = weaponTable.get(weaponId, targetArmor, attackResult);

		int hp;
		TacticalActionState state = TacticalActionState.PENDING_RESOLUTION;
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
				CriticalSeverity severity = CriticalSeverity.valueOf(matcherCrit.group(2));
				CriticalType type = CriticalType.valueOf(matcherCrit.group(3));
				state = TacticalActionState.PENDING_CRITICAL_RESOLUTION;
				context.getAction().setCriticalResult(TacticalCriticalResult.builder()
					.severity(severity)
					.type(type)
					.build());
			}
			else {
				throw new RuntimeException("Invalid result");
			}
		}
		context.getAction().setHpResult(hp);
		context.getAction().setAttackResult(attackResult);
		context.getAction().setState(state);
		return context;
	}

}
