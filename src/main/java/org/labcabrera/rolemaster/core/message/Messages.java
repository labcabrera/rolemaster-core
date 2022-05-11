package org.labcabrera.rolemaster.core.message;

import org.labcabrera.rolemaster.core.model.spell.Realm;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Messages {

	@UtilityClass
	public static class Errors {

		// Templates
		private static final String CHARACTER_NOT_FOUND_T = "Character %s not found.";
		private static final String TACTICAL_SESSION_NOT_FOUND_T = "Tactical session %s not found.";
		private static final String ROUND_NOT_FOUND_T = "Round %s not found.";
		private static final String INVALID_NPC_ID_T = "Invalid NPC identifier %s.";
		private static final String MISSING_CHARACTER_INITIATIVE_T = "Not declared initiative for character %s.";
		private static final String MISSING_ACTION_T = "Action %s not found.";
		private static final String MISSING_ACTION_INITIATIVE_T = "Not declared initiative for action %s.";
		private static final String TACTICAL_SESSION_HAS_NO_ROUND_T = "Tactical session %s has no round.";
		private static final String TRAINING_PACKAGE_NOT_FOUND_T = "Training package %s not found.";
		private static final String CHARACTER_MISSING_SKILL_CATEGORY_T = "Character does not have the skill category %s.";
		private static final String CHARACTER_MISSING_SKILL_T = "Character does not have skill %s.";
		private static final String CHARACTER_ITEM_NOT_FOUND_T = "Character item %s not found.";
		private static final String ITEM_NOT_FOUND_T = "Item %s not found.";

		// Public errors
		public static final String INVALID_ROUND_STATE = "Invalid round state.";
		public static final String ONLY_WEAPONS_OR_SHIELDS_CAN_BE_EQUIPPEN_IN_OFF_HAND = "Only weapons or shields can be equippen in off-hand.";
		public static final String ONLY_WEAPONS_CAN_BE_EQUIPPED_IN_MAIN_HAND = "Only weapons can be equipped in main hand.";
		public static final String ONLY_ARMOR_CAN_BE_IN_POSITION_EQUIPED = "Only armor pieces can be equipped.";

		public static String characterItemNotFound(String characterItemId) {
			return String.format(CHARACTER_ITEM_NOT_FOUND_T, characterItemId);
		}

		public static String characterMissingSkill(String skillId) {
			return String.format(CHARACTER_MISSING_SKILL_T, skillId);
		}

		public static String characterMissingSkillCategory(String skillCategoryId) {
			return String.format(CHARACTER_MISSING_SKILL_CATEGORY_T, skillCategoryId);
		}

		public static String characterNotFound(String characterId) {
			return String.format(CHARACTER_NOT_FOUND_T, characterId);
		}

		public static String invalidNpcId(String npcId) {
			return String.format(INVALID_NPC_ID_T, npcId);
		}

		public static String invalidRealm(Realm realm) {
			return String.format("Invalid realm %s.", realm.getName());
		}

		public static String invalidSkillCategory(String skillCategoryId) {
			return String.format("Invalid skill category %s.", skillCategoryId);
		}

		public static String itemNotFound(String itemId) {
			return String.format(ITEM_NOT_FOUND_T, itemId);
		}

		public static String missingAction(String actionId) {
			return String.format(MISSING_ACTION_T, actionId);
		}

		public static final String missingActionInitiative(String actionId) {
			return String.format(MISSING_ACTION_INITIATIVE_T, actionId);
		}

		public static final String missingChracterInitiative(String characterId) {
			return String.format(MISSING_CHARACTER_INITIATIVE_T, characterId);
		}

		public static final String missingSkill(String skillId) {
			return String.format("Skill %s not found.", skillId);
		}

		public static final String missingStrategicSession(String strategicSessionId) {
			return String.format("Strategic session %s not found.", strategicSessionId);
		}

		public static String professionNotFound(String professionId) {
			return String.format("Profession %s not found.", professionId);
		}

		public static String raceNotFound(String raceId) {
			return String.format("Race %s not found.", raceId);
		}

		public static final String roundNotFound(String roundId) {
			return String.format(ROUND_NOT_FOUND_T, roundId);
		}

		public static final String tacticalSessionHasNoRound(String tacticalSessionId) {
			return String.format(TACTICAL_SESSION_HAS_NO_ROUND_T, tacticalSessionId);
		}

		public static final String tacticalSessionNotFound(String tacticalSessionId) {
			return String.format(TACTICAL_SESSION_NOT_FOUND_T, tacticalSessionId);
		}

		public static String trainingPackageNotFound(String trainingPackageId) {
			return String.format(TRAINING_PACKAGE_NOT_FOUND_T, trainingPackageId);
		}
	}

	@UtilityClass
	public static class TacticalLogs {
		public static final String START_ROUND = "Starting round.";
		public static final String START_INITIATIVE_DECLARATION = "Starting initiative declaration phase.";
		public static final String START_EXECUTION = "Starting execution phase.";
	}

}
