package org.labcabrera.rolemaster.core.service.tactical;

import java.time.LocalDateTime;

import org.labcabrera.rolemaster.core.message.Messages.TacticalLogs;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSessionLog;
import org.labcabrera.rolemaster.core.model.tactical.action.TacticalActionAttack;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterRepository;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class TacticalLogService {

	@Autowired
	private TacticalSessionLogRepository logRepository;

	@Autowired
	private TacticalCharacterRepository characterRepository;

	@Autowired
	private TacticalRoundRepository roundRepository;

	public <E> Mono<E> log(E e, String tacticalSessionId, Integer round, String message) {
		TacticalSessionLog logEntry = TacticalSessionLog.builder()
			.tacticalSessionId(tacticalSessionId)
			.message(message)
			.round(round)
			.created(LocalDateTime.now())
			.build();
		return logRepository.insert(logEntry).map(entry -> e);
	}

	public <E> Mono<E> startRound(E e, String tacticalSessionId, Integer round) {
		return this.log(e, tacticalSessionId, round, TacticalLogs.START_ROUND);
	}

	public <E> Mono<E> startExecution(E e, String tacticalSessionId, Integer round) {
		return this.log(e, tacticalSessionId, round, TacticalLogs.START_EXECUTION);
	}

	public Mono<Void> deleteByTacticalSessionId(String tacticalSessionId) {
		return logRepository.deleteByTacticalSessionId(tacticalSessionId);
	}

	public Mono<TacticalActionAttack> logAttackResult(TacticalActionAttack attack) {
		return Mono.zip(
			Mono.just(attack),
			roundRepository.findById(attack.getId()),
			characterRepository.findById(attack.getSource()),
			characterRepository.findById(attack.getTarget()))
			.map(tuple -> {
				TacticalRound round = tuple.getT2();
				TacticalCharacter source = tuple.getT3();
				TacticalCharacter target = tuple.getT3();
				String message = getAttackMessage(attack, source, target);
				return TacticalSessionLog.builder()
					.tacticalSessionId(round.getTacticalSessionId())
					.round(round.getRound())
					.message(message)
					.created(LocalDateTime.now())
					.build();
			})
			.flatMap(logRepository::save)
			.thenReturn(attack);
	}

	private String getAttackMessage(TacticalActionAttack attack, TacticalCharacter source, TacticalCharacter target) {
		StringBuilder sb = new StringBuilder();
		sb.append("Character ").append(source.getName()).append(" attacks ").append(target.getName());
		sb.append(" for ").append(attack.getAttackResult().getAttackResult());
		sb.append(" (").append(attack.getAttackResult().getWeaponTableId()).append(")");

		if (attack.getAttackResult().getFumbleResult() != null) {
			sb.append(" getting a fumble.");
		}
		else {
			if (attack.getAttackResult().getHp() > 0) {
				sb.append(" causing ").append(attack.getAttackResult().getHp()).append(" hit points");
			}
			else {
				sb.append(" with no effect");
			}
			if (attack.getAttackResult().getCriticalResult() != null) {
				sb.append(" and applying a critical ");
				sb.append(attack.getAttackResult().getCriticalResult().getSeverity());
				sb.append(attack.getAttackResult().getCriticalResult().getType());
				sb.append(" for ");
				sb.append(attack.getAttackResult().getCriticalResult().getCriticalTableResult().getHp());
				sb.append(" extra hitpoints");
				//TODO critical detail
			}
		}
		return sb.append(".").toString();
	}

}
