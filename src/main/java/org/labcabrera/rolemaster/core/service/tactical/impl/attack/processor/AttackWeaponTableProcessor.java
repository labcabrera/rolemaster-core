package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

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
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalCriticalResult;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.table.weapon.WeaponTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class AttackWeaponTableProcessor {

	private static final String PATTERN_HP = "^([0-9]+)$";
	private static final String PATTERN_CRIT = "^([0-9]+)(\\w)(\\w)$";
	private static final int MIN_ATTACK = 1;
	private static final int MAX_ATTACK = 150;

	@Autowired
	private WeaponTable weaponTable;

	@Autowired
	private TacticalCharacterRepository tacticalCharacterRepository;

	public <T extends AttackContext<?>> Mono<T> apply(T context) {
		if (context.getAction().isFlumbe()) {
			return Mono.just(context);
		}
		return Mono.just(context)
			.flatMap(this::processMainHandAttack)
			.flatMap(this::processOffHandAttack)
			.map(ctx -> {
				updateState(ctx.getAction());
				return ctx;
			})
			.flatMap(ctx -> Mono.just(ctx));
	}

	private <T extends AttackContext<?>> Mono<T> processMainHandAttack(T context) {
		CharacterItem mainHandItem = context.getSource().getItems().stream()
			.filter(e -> e.getPosition() == ItemPosition.MAIN_HAND)
			.findFirst().orElse(null);
		if (mainHandItem == null) {
			throw new NotImplementedException("Special attacks");
		}
		String weaponTableId = mainHandItem.getItemId();
		TacticalActionAttack action = context.getAction();
		int targetArmor = getTargetArmor(context.getTarget());
		int offensiveBonus = context.getAction().getOffensiveBonus();
		int primaryRoll = context.getAction().getRoll().getFirstRoll();
		processAttackResult(action, weaponTableId, offensiveBonus, targetArmor, primaryRoll);
		return Mono.just(context);
	}

	private <T extends AttackContext<?>> Mono<T> processOffHandAttack(T context) {
		CharacterItem offHandItem = context.getSource().getItems().stream()
			.filter(e -> e.getPosition() == ItemPosition.OFF_HAND)
			.findFirst().orElse(null);
		if (offHandItem == null) {
			throw new NotImplementedException("Special attacks");
		}
		String weaponTableId = offHandItem.getItemId();
		TacticalActionAttack action = context.getAction();
		//TODO no es este bonus ya que hay que aplicar la bd del otro target, el -20 de offHand, etc...
		int offensiveBonus = context.getAction().getOffensiveBonus();
		int secondaryRoll = context.getAction().getRoll().getFirstRoll();
		int targetArmor = getTargetArmor(context.getTarget());

		return tacticalCharacterRepository.findById(action.getSecondaryTarget())
			.map(e -> e.getArmor())
			.map(armor -> {
				processAttackResult(action, weaponTableId, offensiveBonus, targetArmor, secondaryRoll);
				return context;
			})
			.flatMap(ctx -> Mono.just(ctx));
	}

	private void processAttackResult(TacticalActionAttack action, String weaponTableId, int offensiveBonus, int armor, int roll) {
		int attackResult = Integer.min(MAX_ATTACK, Integer.max(MIN_ATTACK, offensiveBonus + roll));
		String stringResult = weaponTable.get(weaponTableId, armor, attackResult);

		AttackResult result = AttackResult.builder()
			.weaponTableId(weaponTableId)
			.attackResult(attackResult)
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

	private void updateState(TacticalActionAttack action) {
		boolean requiresCriticalResolution = action.getCriticalResults().stream()
			.filter(e -> e.getCriticalTableResult() == null)
			.count() > 0;
		if (requiresCriticalResolution) {
			action.setState(TacticalActionState.PENDING_CRITICAL_RESOLUTION);
		}
		else {
			action.setState(TacticalActionState.PENDING_RESOLUTION);
		}
	}

	private Integer getTargetArmor(TacticalCharacter ta) {
		return ta.getArmor();
	}

}
