package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalType;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackResult;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalCriticalResult;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemResolver;
import org.labcabrera.rolemaster.core.table.weapon.WeaponTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AttackWeaponTableProcessor {

	private static final String PATTERN_HP = "^([0-9]+)$";
	private static final String PATTERN_CRIT = "^([0-9]+)(\\w)(\\w)$";
	private static final int MIN_ATTACK = 1;
	private static final int MAX_ATTACK = 150;

	@Autowired
	private WeaponTable weaponTable;

	@Autowired
	private TacticalCharacterItemResolver itemResolver;

	public <T extends AttackContext<?>> Mono<T> apply(T context) {
		if (context.getAction().isFlumbe()) {
			return Mono.just(context);
		}
		log.debug("Processing weapon table for attack {}", context.getAction().getId());
		return Mono.just(context)
			.map(this::processMainHandAttack)
			.map(this::processOffHandAttack)
			.map(this::updateState);
	}

	private <T extends AttackContext<?>> T processMainHandAttack(T context) {
		log.debug("Processing weapon main attack table for attack {}", context.getAction().getId());
		return processAttack(context, AttackTargetType.MAIN_HAND);
	}

	private <T extends AttackContext<?>> T processOffHandAttack(T context) {
		log.debug("Processing weapon off-hand table attack for attack {}", context.getAction().getId());
		if (context.getAction().getTargets().size() != 2) {
			return context;
		}
		return processAttack(context, AttackTargetType.OFF_HAND);
	}

	private <T extends AttackContext<?>> T processAttack(T context, AttackTargetType type) {
		ItemPosition itemPosition = type == AttackTargetType.MAIN_HAND ? ItemPosition.MAIN_HAND : ItemPosition.OFF_HAND;
		CharacterItem mainHandItem = itemResolver.getWeapon(context.getSource(), itemPosition);
		if (mainHandItem == null) {
			throw new NotImplementedException("Special attacks");
		}
		String weaponTableId = mainHandItem.getItemId();
		TacticalActionAttack action = context.getAction();
		TacticalCharacter target = context.getTargets().get(type);
		int targetArmor = target.getArmor();
		Map<?, Integer> offensiveBonusmap = context.getAction().getOffensiveBonusMap().get(type);
		int bonus = offensiveBonusmap.values().stream().reduce(0, (a, b) -> a + b);
		int primaryRoll = context.getAction().getRolls().get(type).getResult();
		processAttackResult(action, target.getId(), weaponTableId, bonus, targetArmor, primaryRoll);
		return context;
	}

	private void processAttackResult(TacticalActionAttack action, String target, String weaponTableId, int offensiveBonus, int armor,
		int roll) {

		int attackResult = offensiveBonus + roll;
		int tableAttackResult = Integer.min(MAX_ATTACK, Integer.max(MIN_ATTACK, attackResult));
		String stringResult = weaponTable.get(weaponTableId, armor, tableAttackResult);

		AttackResult result = AttackResult.builder()
			.target(target)
			.weaponTableId(weaponTableId)
			.result(attackResult)
			.totalBonus(offensiveBonus)
			.targetArmor(armor)
			.hp(0)
			.build();
		action.getAttackResults().add(result);

		Pattern patternHp = Pattern.compile(PATTERN_HP);
		Matcher matcherHp = patternHp.matcher(stringResult);
		if (matcherHp.matches()) {
			int hp = Integer.parseInt(matcherHp.group(1));
			result.setHp(hp);
		}
		else {
			Pattern patternCrit = Pattern.compile(PATTERN_CRIT);
			Matcher matcherCrit = patternCrit.matcher(stringResult);
			if (matcherCrit.matches()) {
				int hp = Integer.parseInt(matcherCrit.group(1));
				CriticalSeverity severity = CriticalSeverity.valueOf(matcherCrit.group(2));
				CriticalType type = CriticalType.valueOf(matcherCrit.group(3));
				result.setHp(hp);
				TacticalCriticalResult tcr = TacticalCriticalResult.builder()
					.severity(severity)
					.type(type)
					.build();
				action.getCriticalResults().add(tcr);
			}
			else {
				throw new NotImplementedException("Invalid result format " + attackResult);
			}
		}

	}

	private <T extends AttackContext<?>> T updateState(T context) {
		TacticalActionAttack action = context.getAction();
		boolean requiresCriticalResolution = action.getCriticalResults().stream()
			.filter(e -> e.getCriticalTableResult() == null)
			.count() > 0;
		if (requiresCriticalResolution) {
			action.setState(TacticalActionState.PENDING_CRITICAL_RESOLUTION);
		}
		else {
			action.setState(TacticalActionState.PENDING_RESOLUTION);
		}
		return context;
	}

}
