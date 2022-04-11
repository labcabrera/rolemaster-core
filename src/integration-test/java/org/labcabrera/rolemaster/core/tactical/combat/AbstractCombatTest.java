package org.labcabrera.rolemaster.core.tactical.combat;

import org.labcabrera.rolemaster.core.repository.StrategicSessionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalActionRepository;
import org.labcabrera.rolemaster.core.repository.TacticalCharacterContextRepository;
import org.labcabrera.rolemaster.core.repository.TacticalRoundRepository;
import org.labcabrera.rolemaster.core.repository.TacticalSessionRepository;
import org.labcabrera.rolemaster.core.service.tactical.TacticalActionService;
import org.labcabrera.rolemaster.core.service.tactical.TacticalService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCombatTest {

	@Autowired
	protected TacticalService tacticalService;

	@Autowired
	protected TacticalActionService tacticalActionService;

	@Autowired
	protected StrategicSessionRepository strategicSessionRepository;

	@Autowired
	protected TacticalSessionRepository tacticalSessionRepository;

	@Autowired
	protected TacticalRoundRepository roundRepository;

	@Autowired
	protected TacticalActionRepository tacticalActionRepository;

	@Autowired
	protected TacticalCharacterContextRepository tacticalCharacterRepository;

	protected void clearData() {
		strategicSessionRepository.deleteAll()
			.then(tacticalSessionRepository.deleteAll())
			.then(roundRepository.deleteAll())
			.then(tacticalActionRepository.deleteAll())
			.then(tacticalCharacterRepository.deleteAll())
			.share().block();
	}
}
