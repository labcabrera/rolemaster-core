package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import java.util.Collection;
import java.util.EnumMap;

import org.labcabrera.rolemaster.core.dto.action.execution.MissileAttackExecution;
import org.labcabrera.rolemaster.core.exception.BadRequestException;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackTargetType;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionMissileAttack;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackResultProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.AttackWeaponTableProcessor;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.MissileAttackContext;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.OffensiveBonusProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class MissileAttackExecutionService {

	@Autowired
	private TacticalActionRepository actionRepository;

	@Autowired
	private TacticalCharacterRepository characterRepository;

	@Autowired
	private OffensiveBonusProcessor offensiveBonusProcessor;

	@Autowired
	private AttackWeaponTableProcessor attackWeaponTableProcessor;

	@Autowired
	private AttackResultProcessor attackResultProcessor;

	public Mono<TacticalActionMissileAttack> execute(TacticalActionMissileAttack action, MissileAttackExecution execution) {
		action.setDistance(execution.getDistance());
		action.setCover(execution.getCover());
		action.setRolls(new EnumMap<>(AttackTargetType.class));
		action.getRolls().put(AttackTargetType.MAIN_HAND, execution.getRoll());

		MissileAttackContext context = new MissileAttackContext();
		context.setAction(action);

		return Mono.just(context)
			.zipWith(characterRepository.findById(context.getAction().getSource()), (a, b) -> a.<MissileAttackContext>setSource(b))
			.flatMap(this::loadTargets)
			.flatMap(offensiveBonusProcessor::apply)
			.flatMap(attackWeaponTableProcessor::apply)
			.flatMap(act -> attackResultProcessor.apply(action))
			.flatMap(actionRepository::save)
			.map(TacticalActionMissileAttack.class::cast);
	}

	private Mono<MissileAttackContext> loadTargets(MissileAttackContext context) {
		Collection<String> ids = context.getAction().getTargets().values();
		return characterRepository.findAllById(ids).collectList()
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
