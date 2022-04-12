package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor;

import org.labcabrera.rolemaster.core.model.character.inventory.CharacterWeapon;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class WeaponLoaderProcessor {

	public <T extends AttackContext<?, ?>> Mono<T> apply(T context) {
		CharacterWeapon mainHandWeapon = context.getTarget().getInventory().getMainHandWeapon();

		return Mono.just(context);
	}

	//	@SuppressWarnings("unchecked")
	//	public <T extends Something<E>, E extends Enum<E> & SomethingAware> T getSomething(Class<T> clazz) {
	//	        return (T) somethingHolderMap.get(clazz);
	//	    }
}
