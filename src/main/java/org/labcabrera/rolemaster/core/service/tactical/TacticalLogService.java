package org.labcabrera.rolemaster.core.service.tactical;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.labcabrera.rolemaster.core.message.Messages.TacticalLogs;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.TacticalRound;
import org.labcabrera.rolemaster.core.model.tactical.TacticalSessionLog;
import org.labcabrera.rolemaster.core.model.tactical.action.AttackResult;
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
			characterRepository.findById(attack.getTargets().values().iterator().next()))
			.map(tuple -> getAttackMessage(attack, tuple.getT2(), tuple.getT3(), tuple.getT4()))
			.flatMapMany(logRepository::saveAll)
			.collectList()
			.thenReturn(attack);
	}

	private List<TacticalSessionLog> getAttackMessage(TacticalActionAttack attack, TacticalRound round, TacticalCharacter source,
		TacticalCharacter target) {
		List<TacticalSessionLog> result = new ArrayList<>();
		for (AttackResult ar : attack.getAttackResults()) {
			StringBuilder sb = new StringBuilder();
			sb.append("Character ").append(source.getName()).append(" attacks ").append(target.getName());
			sb.append(" for ").append(ar.getAttackResult());
			sb.append(" (").append(ar.getWeaponTableId()).append(")");
			//TODO fumble / criticals
			sb.append(".");
			TacticalSessionLog tsl = TacticalSessionLog.builder()
				.tacticalSessionId(round.getTacticalSessionId())
				.round(round.getRound())
				.message(sb.toString())
				.created(LocalDateTime.now())
				.build();
			result.add(tsl);
		}
		return result;
	}

}
