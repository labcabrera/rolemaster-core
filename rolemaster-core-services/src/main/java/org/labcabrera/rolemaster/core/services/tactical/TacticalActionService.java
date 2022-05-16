package org.labcabrera.rolemaster.core.services.tactical;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.labcabrera.rolemaster.core.dto.action.declaration.TacticalActionDeclaration;
import org.labcabrera.rolemaster.core.dto.action.execution.AttackCriticalExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.FumbleExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.TacticalActionExecution;
import org.labcabrera.rolemaster.core.dto.action.execution.WeaponBreakageExecution;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalAction;

import reactor.core.publisher.Mono;

public interface TacticalActionService {

	Mono<TacticalAction> delare(@NotNull @Valid TacticalActionDeclaration request);

	Mono<TacticalAction> getDeclaredAction(@NotEmpty String actionId);

	Mono<Void> removeDeclaredAction(@NotEmpty String actionId);

	Mono<TacticalAction> execute(@NotEmpty String actionId, @NotNull @Valid TacticalActionExecution request);

	Mono<TacticalAction> executeCritical(@NotEmpty String actionId, @NotNull @Valid AttackCriticalExecution execution);

	Mono<TacticalAction> executeFumble(@NotEmpty String actionId, @NotNull @Valid FumbleExecution execution);

	Mono<TacticalAction> executeBreakage(@NotEmpty String actionId, @NotNull @Valid WeaponBreakageExecution execution);

}