package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.exception.DataConsistenceException;
import org.labcabrera.rolemaster.core.model.character.SpecialAttack;
import org.labcabrera.rolemaster.core.model.character.SpecialAttackSize;
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
import org.labcabrera.rolemaster.core.service.table.weapon.WeaponTableService;
import org.labcabrera.rolemaster.core.service.tactical.attack.processor.AbstractAttackProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AttackWeaponTableProcessor implements AbstractAttackProcessor {

	private static final String PATTERN_HP = "^([0-9]+)$";
	private static final String PATTERN_CRIT = "^([0-9]+)(\\w)(\\w)$";
	private static final int MIN_ATTACK = 1;
	private static final int MAX_ATTACK = 150;

	@Autowired
	private WeaponTableService weaponTable;

	@Autowired
	private TacticalCharacterItemResolver itemResolver;

	public Mono<AttackContext> apply(AttackContext context) {
		if (context.getAction().getState() != TacticalActionState.PENDING) {
			return Mono.just(context);
		}
		log.debug("Processing weapon table for attack {}", context.getAction().getId());
		return Mono.just(context)
			.map(this::processMainHandAttack)
			.map(this::processOffHandAttack)
			.map(this::updateState);
	}

	private AttackContext processMainHandAttack(AttackContext context) {
		log.debug("Processing weapon main attack table for attack {}", context.getAction().getId());
		return processAttack(context, AttackTargetType.MAIN_HAND);
	}

	private AttackContext processOffHandAttack(AttackContext context) {
		log.debug("Processing weapon off-hand table attack for attack {}", context.getAction().getId());
		if (context.getAction().getTargets().size() != 2) {
			return context;
		}
		return processAttack(context, AttackTargetType.OFF_HAND);
	}

	private AttackContext processAttack(AttackContext context, AttackTargetType type) {
		String weaponTableId;
		int maxResult = 150;
		if (context.getAction().getSpecialAttack() != null) {
			String specialAttackName = context.getAction().getSpecialAttack();
			SpecialAttack sa = context.getSource().getSpecialAttacks().stream()
				.filter(e -> e.getName().equals(specialAttackName))
				.findFirst().orElseThrow(() -> new DataConsistenceException("Missing special attack " + specialAttackName + "."));
			weaponTableId = sa.getWeaponTableId();
			maxResult = getSpecialAttackMaxResult(sa.getSize());
		}
		else {
			ItemPosition itemPosition = type == AttackTargetType.MAIN_HAND ? ItemPosition.MAIN_HAND : ItemPosition.OFF_HAND;
			CharacterItem mainHandItem = itemResolver.getWeapon(context.getSource(), itemPosition);
			if (mainHandItem == null) {
				throw new NotImplementedException("Special attacks");
			}
			weaponTableId = mainHandItem.getItemId();
		}
		TacticalActionAttack action = context.getAction();
		TacticalCharacter target = context.getTargets().get(type);
		int targetArmor = target.getArmor();
		Map<?, Integer> offensiveBonusmap = context.getAction().getOffensiveBonusMap().get(type);
		int bonus = offensiveBonusmap.values().stream().reduce(0, (a, b) -> a + b);
		int primaryRoll = context.getAction().getRolls().get(type).getResult();
		int result = Math.min(maxResult, bonus + primaryRoll);
		processAttackResult(action, type, weaponTableId, targetArmor, result, bonus);
		return context;
	}

	private void processAttackResult(TacticalActionAttack action, AttackTargetType type, String weaponTableId, int armor,
		int attackResult, int bonus) {
		int tableAttackResult = Integer.min(MAX_ATTACK, Integer.max(MIN_ATTACK, attackResult));
		String stringResult = weaponTable.get(weaponTableId, armor, tableAttackResult);
		AttackResult result = AttackResult.builder()
			.weaponTableId(weaponTableId)
			.result(attackResult)
			.totalBonus(bonus)
			.targetArmor(armor)
			.hp(0)
			.build();
		action.getAttackResults().put(type, result);
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
				CriticalType criticalType = CriticalType.valueOf(matcherCrit.group(3));
				result.setHp(hp);
				TacticalCriticalResult tcr = TacticalCriticalResult.builder()
					.severity(severity)
					.type(criticalType)
					.build();
				action.addCriticalResult(tcr, type);
				action.setState(TacticalActionState.PENDING_CRITICAL_RESOLUTION);
			}
			else {
				throw new NotImplementedException("Invalid result format " + attackResult);
			}
		}

	}

	private AttackContext updateState(AttackContext context) {
		TacticalActionAttack action = context.getAction();
		boolean requiresCriticalResolution = action.hasUnresolvedCritical();
		if (requiresCriticalResolution) {
			action.setState(TacticalActionState.PENDING_CRITICAL_RESOLUTION);
		}
		else {
			action.setState(TacticalActionState.PENDING_RESOLUTION);
		}
		return context;
	}

	private int getSpecialAttackMaxResult(SpecialAttackSize size) {
		switch (size) {
		case TINY, SMALL:
			return 105;
		case MEDIUM:
			return 120;
		case LARGE:
			return 135;
		default:
			return 150;
		}

	}

}
