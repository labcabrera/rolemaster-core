package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.model.character.inventory.CharacterWeapon;
import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackResult;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalCriticalResult;
import org.labcabrera.rolemaster.core.table.weapon.WeaponTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttackWeaponTableProcessor {

	private static final String PATTERN_HP = "^([0-9]+)$";
	private static final String PATTERN_CRIT = "^([0-9]+)(\\w)(\\w)$";
	private static final int MIN_ATTACK = 1;
	private static final int MAX_ATTACK = 150;

	@Autowired
	private WeaponTable weaponTable;

	public <T extends AttackContext<?>> T apply(T context) {
		if (context.getAction().isFlumbe()) {
			return context;
		}
		TacticalActionAttack action = context.getAction();

		CharacterWeapon primaryWeapon = context.getSource().getInventory().getMainHandWeapon();
		String weaponTableId = primaryWeapon.getWeaponTableId();

		int targetArmor = getTargetArmor(context.getTarget());
		int offensiveBonus = context.getAction().getOffensiveBonus();
		int primaryRoll = context.getAction().getRoll().getFirstRoll();

		// Primary attack
		AttackResult primaryAttack = getAttackResult(weaponTableId, offensiveBonus, targetArmor, primaryRoll);
		action.setAttackResult(primaryAttack);

		updateState(action);
		return context;
	}

	private AttackResult getAttackResult(String weaponTableId, int offensiveBonus, int armor, int roll) {
		int attackResult = Integer.min(MAX_ATTACK, Integer.max(MIN_ATTACK, offensiveBonus + roll));
		String stringResult = weaponTable.get(weaponTableId, armor, attackResult);

		AttackResult result = AttackResult.builder().build();

		Pattern patternHp = Pattern.compile(PATTERN_HP);
		Matcher matcherHp = patternHp.matcher(stringResult);
		if (matcherHp.matches()) {
			int hp = Integer.parseInt(matcherHp.group(1));
			result.setHpResult(hp);
		}
		else {
			Pattern patternCrit = Pattern.compile(PATTERN_CRIT);
			Matcher matcherCrit = patternCrit.matcher(stringResult);
			if (matcherCrit.matches()) {
				int hp = Integer.parseInt(matcherCrit.group(1));
				CriticalSeverity severity = CriticalSeverity.valueOf(matcherCrit.group(2));
				CriticalType type = CriticalType.valueOf(matcherCrit.group(3));
				result.setHpResult(hp);
				result.setCriticalResult(TacticalCriticalResult.builder()
					.severity(severity)
					.type(type)
					.build());
			}
			else {
				throw new NotImplementedException("Invalid result format " + attackResult);
			}
		}
		return result;
	}

	private void updateState(TacticalActionAttack action) {
		boolean resolved = true;
		if (action.getAttackResult().requiresCriticalResolution()) {
			resolved = false;
		}
		if (resolved) {
			action.setState(TacticalActionState.PENDING_RESOLUTION);
		}
		else {
			action.setState(TacticalActionState.PENDING_CRITICAL_RESOLUTION);
		}
	}

	private Integer getTargetArmor(TacticalCharacter ta) {
		return ta.getInventory().getEquipedArmor();
	}

}