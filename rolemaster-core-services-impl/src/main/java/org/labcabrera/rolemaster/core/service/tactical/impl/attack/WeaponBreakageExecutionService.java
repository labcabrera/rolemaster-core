package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import java.util.Map.Entry;

import org.labcabrera.rolemaster.core.dto.action.execution.WeaponBreakageExecution;
import org.labcabrera.rolemaster.core.dto.context.AttackContext;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.character.item.CharacterItem;
import org.labcabrera.rolemaster.core.model.character.item.ItemFeature;
import org.labcabrera.rolemaster.core.model.character.item.ItemFeatureType;
import org.labcabrera.rolemaster.core.model.character.item.ItemPosition;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackBreakageResult;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.repository.CharacterItemRepository;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.service.context.loader.AttackContextLoader;
import org.labcabrera.rolemaster.core.service.tactical.impl.TacticalCharacterItemResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class WeaponBreakageExecutionService {

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private AttackProcessorService processorService;

	@Autowired
	private AttackContextLoader contextLoader;

	@Autowired
	private TacticalCharacterItemResolver itemResolver;

	@Autowired
	private CharacterItemRepository characterItemRepository;

	@Autowired
	private TacticalCharacterRepository tacticalCharacterRepository;

	public Mono<TacticalActionAttack> apply(TacticalActionAttack action, WeaponBreakageExecution execution) {
		if (action.getState() != TacticalActionState.PENDING_BREAKAGE_RESOLUTION) {
			return Mono.just(action);
		}
		log.debug("Processing weapon breakage");
		loadBreakageRolls(action, execution);
		return Mono.just(AttackContext.builder().action(action).build())
			.flatMap(contextLoader::apply)
			.map(this::processBreakage)
			.flatMap(this::processWeaponBreakage)
			.map(this::processAttackState)
			.flatMap(processorService::apply)
			.map(AttackContext::getAction)
			.flatMap(actionRepository::save)
			.map(TacticalActionAttack.class::cast);
	}

	private void loadBreakageRolls(TacticalActionAttack action, WeaponBreakageExecution execution) {
		if (action.getBreakageResults() == null || action.getBreakageResults().isEmpty()) {
			throw new BadRequestException("Action has no breakage results.");
		}
		if (action.getBreakageResults().size() != execution.getRolls().size()) {
			throw new BadRequestException("Invalid roll count.");
		}
		execution.getRolls().entrySet().stream().forEach(pair -> {
			AttackTargetType type = pair.getKey();
			if (!action.getBreakageResults().containsKey(type)) {
				throw new BadRequestException("Invalid breakage roll for " + type + ".");
			}
			action.getBreakageResults().get(type).setRoll(pair.getValue());
		});
	}

	private AttackContext processBreakage(AttackContext context) {
		context.getAction().getBreakageResults().entrySet().forEach(entry -> {
			AttackBreakageResult br = entry.getValue();
			int modifiersBonus = br.getModifiers().entrySet().stream()
				.map(Entry::getValue)
				.reduce(0, (a, b) -> a + b);
			int result = br.getStrength() + br.getRoll() + modifiersBonus;
			br.setResult(result);
			boolean breakage = result < 100;
			br.setWeaponBreakage(breakage);
		});
		return context;
	}

	private Mono<AttackContext> processWeaponBreakage(AttackContext context) {
		return Mono.just(context)
			.flatMap(ctx -> processWeaponBreakage(ctx, AttackTargetType.MAIN_HAND))
			.flatMap(ctx -> processWeaponBreakage(ctx, AttackTargetType.OFF_HAND));
	}

	private Mono<AttackContext> processWeaponBreakage(AttackContext context, AttackTargetType type) {
		if (!context.getAction().getBreakageResults().containsKey(type)) {
			return Mono.just(context);
		}
		if (!Boolean.TRUE.equals(context.getAction().getBreakageResults().get(type).getWeaponBreakage())) {
			return Mono.just(context);
		}
		ItemPosition position = type == AttackTargetType.MAIN_HAND ? ItemPosition.MAIN_HAND : ItemPosition.OFF_HAND;
		CharacterItem item = itemResolver.getItem(context.getSource(), position);
		item.getFeatures().add(ItemFeature.builder()
			.type(ItemFeatureType.BROKEN)
			.build());
		if (item.getId() != null) {
			return characterItemRepository.save(item)
				.thenReturn(context.getSource())
				.flatMap(tacticalCharacterRepository::save)
				.thenReturn(context);
		}
		else {
			return Mono.just(context.getSource())
				.flatMap(tacticalCharacterRepository::save)
				.thenReturn(context);
		}
	}

	/**
	 * In two-weapon combat, if at least one weapon is not broken, the combat is
	 * processed.
	 * @param context
	 * @return
	 */
	private AttackContext processAttackState(AttackContext context) {
		boolean check = context.getAction().getBreakageResults().entrySet().stream()
			.map(Entry::getValue)
			.filter(e -> !e.getWeaponBreakage())
			.count() > 0;
		TacticalActionState state = check ? TacticalActionState.PENDING : TacticalActionState.RESOLVED;
		context.getAction().setState(state);
		return context;
	}

}
