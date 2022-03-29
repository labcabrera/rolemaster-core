package org.labcabrera.rolemaster.core.model.spell;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum SpellSubtype {

	S("subconscious", "Subconscious spell. These spells are capable of being cast (or triggered) by the subconscious. The caster can "
		+ "always cast a spell of this type as a normal spell. In addition, any subconscious spell can be triggered while "
		+ "the caster is unconscious, asleep, in a trance, etc. A subconscious spell can be triggered by conditions that the "
		+ "spell can affect (e.g., a Regenerate spell will trigger if the caster is knocked out and has unhealed concussion hits). "
		+ "A Stun Relief spell may be cast normally while stunned (or stunned no parry) with no penalty. Similarly, an Awake "
		+ "spell may be cast while unconscious or asleep if circumstances alert the caster/spell’s senses. In both cases, it is "
		+ "assumed that the spell is triggered subconsciously. A GM may allow a character to set conditions on his "
		+ "subconscious spells by “programming” his subconscious (e.g., a character might decide to only trigger Regenerate if "
		+ "he takes hits and goes unconscious in combat and not if he just trips and falls and knocks himself out). In such a "
		+ "case, the GM may require a maneuver roll to use the “programming” modified by +50 plus three times the "
		+ "character’s Self Discipline plus any skill developed for this spell list."),

	M("mental", "Mental Attack spell. Any spell marked with an ‘m’ is considered a mental attack spell, and is subject to effects "
		+ "and defenses that target mental or mind attacks. It should also be noted that these spells are ineffective against any "
		+ "creature or entity that does not have a “mind” per se (e.g., Undead, plants, politicians, etc.)");

	private String name;

	@Getter
	private String description;

	private SpellSubtype(String name, String description) {
		this.name = name;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
