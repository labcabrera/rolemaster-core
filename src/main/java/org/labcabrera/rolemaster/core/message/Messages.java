package org.labcabrera.rolemaster.core.message;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Messages {

	@UtilityClass
	public static class Errors {
		public static final String CHARACTER_NOT_FOUND_T = "Character %s not found.";
		public static final String TACTICAL_SESSION_NOT_FOUND_T = "Tactical session %s not found.";
		public static final String ROUND_NOT_FOUND_T = "Round %s not found.";
		public static final String INVALID_NPC_ID_T = "Invalid NPC identifier %s.";
		public static final String MISSING_CHARACTER_INITIATIVE_T = "Not declared initiative for character %s.";
		public static final String MISSING_ACTION_T = "Action %s not found.";
		public static final String MISSING_ACTION_INITIATIVE_T = "Not declared initiative for action %s.";
		public static final String TACTICAL_SESSION_HAS_NO_ROUND_T = "Tactical session %s has no round.";
		public static final String TRAINING_PACKAGE_NOT_FOUND_T = "Training package %s not found.";
		public static final String CHARACTER_MISSING_SKILL_CATEGORY_T = "Character does not have the skill category %s.";
		public static final String CHARACTER_MISSING_SKILL_T = "Character does not have skill %s.";
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

		public static String characterNotFound(String characterId) {
			return String.format(CHARACTER_NOT_FOUND_T, characterId);
		}

		public static String trainingPackageNotFound(String trainingPackageId) {
			return String.format(TRAINING_PACKAGE_NOT_FOUND_T, trainingPackageId);
		}

		public static String characterMissingSkillCategory(String skillCategoryId) {
			return String.format(CHARACTER_MISSING_SKILL_CATEGORY_T, skillCategoryId);
		}

		public static String characterMissingSkill(String skillId) {
			return String.format(CHARACTER_MISSING_SKILL_CATEGORY_T, skillId);
		}

		public static String missingAction(String actionId) {
			return String.format(MISSING_ACTION_T, actionId);
		}
	}

	@UtilityClass
	public static class TacticalLogs {
		public static final String START_ROUND = "Starting round.";
		public static final String START_INITIATIVE_DECLARATION = "Starting initiative declaration phase.";
		public static final String START_EXECUTION = "Starting execution phase.";
	}

}
