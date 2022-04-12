package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import java.util.List;

import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.item.WeaponCategory;
import org.labcabrera.rolemaster.core.model.tactical.TacticalActionState;
import org.labcabrera.rolemaster.core.model.tactical.actions.AttackFumbleResult;
import org.labcabrera.rolemaster.core.model.tactical.actions.FumbleType;
import org.labcabrera.rolemaster.core.repository.WeaponRepository;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.TacticalAttackContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AttackFumbleProcessor {

	@Autowired
	private WeaponRepository weaponRepository;

	public Mono<TacticalAttackContext> apply(TacticalAttackContext context) {
		log.debug("Checking fumble for attack {}", context.getAction().getId());
		if (context.getAction().getRoll() == null) {
			throw new RuntimeException("Expected roll");
		}
		String weaponId = context.getSource().getItems().getMainWeaponId();
		return Mono.just(context)
			.zipWith(weaponRepository.findById(weaponId))
			.map(pair -> {
				TacticalAttackContext ctx = pair.getT1();
				WeaponCategory weapon = pair.getT2();
				OpenRoll roll = context.getAction().getRoll();
				List<Integer> values = weapon.getFumble();
				if (values.contains(roll.getRolls().iterator().next())) {
					ctx.getAction().setFumbleResult(AttackFumbleResult.builder()
						.type(getFumbleType())
						.build());
					ctx.getAction().setState(TacticalActionState.PENDING_FUMBLE_RESOLUTION);
				}
				return ctx;
			});
	}

	private FumbleType getFumbleType() {
		return FumbleType.HAND_1H;
	}

}
