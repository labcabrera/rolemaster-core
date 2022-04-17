package org.labcabrera.rolemaster.core.message;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Message {

	@UtilityClass
	public static class Errors {
		public static final String TACTICAL_SESSION_NOT_FOUND_T = "Tactical session %s not found.";
		public static final String ROUND_NOT_FOUND_T = "Round %s not found.";
		public static final String INVALID_NPC_ID_T = "Invalid NPC identifier %s.";
		public static final String MISSING_CHARACTER_INITIATIVE_T = "Not declared initiative for character %s.";
		public static final String MISSING_ACTION_INITIATIVE_T = "Not declared initiative for action %s.";
		public static final String TACTICAL_SESSION_HAS_NO_ROUND_T = "Tactical session %s has no round.";

		public static final String INVALID_ROUND_STATE = "Invalid round state.";

		public static final String tacticalSessionNotFound(String tacticalSessionId) {
			return String.format(TACTICAL_SESSION_NOT_FOUND_T, tacticalSessionId);
		}

		public static final String roundNotFound(String roundId) {
			return String.format(ROUND_NOT_FOUND_T, roundId);
		}

		public static final String invalidNpcId(String npcId) {
			return String.format(INVALID_NPC_ID_T, npcId);
		}

		public static final String missingChracterInitiative(String characterId) {
			return String.format(MISSING_CHARACTER_INITIATIVE_T, characterId);
		}

		public static final String missingActionInitiative(String actionId) {
			return String.format(MISSING_ACTION_INITIATIVE_T, actionId);
		}

		public static final String tacticalSessionHasNoRound(String tacticalSessionId) {
			return String.format(TACTICAL_SESSION_HAS_NO_ROUND_T, tacticalSessionId);
		}
	}

	@UtilityClass
	public static class TacticalLogs {
		public static final String START_ROUND = "Starting round.";
		public static final String START_INITIATIVE_DECLARATION = "Starting initiative declaration phase.";
		public static final String START_EXECUTION = "Starting execution phase.";
	}

}
