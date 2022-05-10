package org.labcabrera.rolemaster.core.controller.impl;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.labcabrera.rolemaster.core.controller.WeaponController;
import org.labcabrera.rolemaster.core.table.weapon.WeaponTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class WeaponControllerImpl implements WeaponController {

	@Autowired
	private WeaponTableService weaponTable;

	@Override
	public Mono<List<String>> getLoadedWeapons() {
		return Mono.just(weaponTable.getWeapons());
	}

	@Override
	public Mono<String> findById(String weaponId, @Min(1) @Max(20) Integer armor, @Min(1) @Max(150) Integer roll) {
		return Mono.just(weaponTable.get(weaponId, armor, roll));
	}

}
