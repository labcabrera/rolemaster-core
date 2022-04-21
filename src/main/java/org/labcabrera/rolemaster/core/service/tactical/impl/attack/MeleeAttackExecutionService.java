package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.labcabrera.rolemaster.core.dto.action.execution.MeleeAttackExecution;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.MeleeAttackMode;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackFumbleProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackResultProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackWeaponTableProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.OffensiveBonusProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.melee.MeleeAttackContext;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.melee.MeleeAttackDefensiveBonusProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class MeleeAttackExecutionService {

	@Autowired
	private TacticalCharacterRepository tacticalCharacterRepository;

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private OffensiveBonusProcessor offensiveBonusProcessor;

	@Autowired
	private MeleeAttackDefensiveBonusProcessor defensiveBonusProcessor;

	@Autowired
	private AttackWeaponTableProcessor weaponTableProcessor;

	@Autowired
	private AttackFumbleProcessor fumbleProcessor;

	@Autowired
	private AttackResultProcessor attackResultProcessor;

	public Mono<TacticalActionMeleeAttack> execute(TacticalActionMeleeAttack action, MeleeAttackExecution execution) {
		loadTargets(action, execution);
		action.setRolls(execution.getRolls());
		action.setFacingMap(execution.getFacingMap());

		MeleeAttackContext context = new MeleeAttackContext();
		context.setAction(action);

		return Mono.just(context)
			.zipWith(tacticalCharacterRepository.findById(context.getAction().getSource()), (a, b) -> a.<MeleeAttackContext>setSource(b))
			.flatMap(this::loadTargets)
			.flatMap(fumbleProcessor::apply)
			.flatMap(offensiveBonusProcessor::apply)
			.flatMap(defensiveBonusProcessor::apply)
			.flatMap(weaponTableProcessor::apply)
			.flatMap(ctx -> attackResultProcessor.apply(ctx.getAction()))
			.flatMap(actionRepository::save)
			.map(TacticalActionMeleeAttack.class::cast);
	}

	private void loadTargets(TacticalActionMeleeAttack action, MeleeAttackExecution execution) {
		switch (action.getMeleeAttackType()) {
		case FULL:
			//			if (!execution.getTargets().isEmpty()) {
			//				throw new BadRequestException("Can not declare target in full melee attack type");
			//			}
			break;
		case PRESS_AND_MELEE, REACT_AND_MELEE:
			if (execution.getTargets().isEmpty()) {
				throw new BadRequestException("Required target");
			}
			if (action.getMeleeAttackMode() == MeleeAttackMode.TWO_WEAPONS && execution.getTargets().size() != 2) {
				throw new BadRequestException("Expected 2 targets using two weapon attacks");
			}
			action.setTargets(execution.getTargets());
			break;
		default:
			break;
		}
	}

	private Mono<MeleeAttackContext> loadTargets(MeleeAttackContext context) {
		List<String> ids = new ArrayList<>(new HashSet<>(context.getAction().getTargets().values()));
		return tacticalCharacterRepository.findAllById(ids).collectList()
			.map(list -> {
				if (list.size() != ids.size()) {
					throw new BadRequestException("Invalid targets");
				}
				context.getAction().getTargets().entrySet().stream().forEach(e -> {
					AttackTargetType key = e.getKey();
					String value = e.getValue();
					TacticalCharacter tc = list.stream().filter(i -> value.equals(e.getValue())).findFirst().orElseThrow();
					context.getTargets().put(key, tc);
				});
				return context;
			})
			.flatMap(ctx -> Mono.just(context));
	}

}
