package org.labcabrera.rolemaster.core.model.spell;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;

public enum SpellType {

	E("elemental", "Elemental spell. These spells use the force of the spell to manipulate physical elements (heat, cold, wind, light, "
		+ "water, earth, sound, smell, taste, touch). These elements (and not the spell) are used to either create a phenomena "
		+ "that can affect the physical environment of the target (e.g., a “wall” spell) or the sense of the target (e.g., an "
		+ "“illusion” spell). Because the elements are real, no Resistance Rolls are normally allowed."),

	BE("ballElemental", "Ball Elemental spell. These are elemental spells that attack an area with one of the physical elements. Such "
		+ "attacks are resolved on the Cold Ball Attack Table 13.3 (p. 82) or the Fire Ball Attack Table 13.4 (p. 83)."),

	DE("directedElemental", "Directed Elemental spell. These are elemental spells that directly attack a target with one of the physical "
		+ "elements. Such attacks are resolved on the Bolt Attack Tables 13.5-13.9 (p. 84-88)."),

	F("force", "Force spell. These spells involve the direct manipulation of matter, energy, the elements, or living beings "
		+ "through the use of a spell’s force. If the spell has a target capable of resisting, the caster makes an attack roll on "
		+ "the Basic Spell Attack Table 13.1 (p. 81) to determine the RR modification for the target. Determine the type "
		+ "of armor the target is wearing and roll on the appropriate column of the table (using the Other column if nothing "
		+ "else applies). After determining the RR modification, the target makes an RR (on Table 13.2, p. 81, using "
		+ "the target’s level and the attacker’s level as the indices)."),

	P("passive", "Passive spell. These spells usually only indirectly or passively affect a target. Thus, if an RR is allowed (GM’s "
		+ "discretion), its purpose is only to determine if the target is aware of the spell. Many detection spells are of this type."),

	U("utility", "Utility spell. These spells only affect the caster, a willing target, or a target incapable of resistance. Thus, "
		+ "RRs are not usually necessary. A willing target who is capable of resisting may still be required to make an RR "
		+ "(GM’s discretion), but it is modified by -50 (i.e., he mostly likely will not resist). Most healing spells are of this type."),

	I("infrormational", "Informational spell. These spells involve gathering information through means that do not require RRs.");

	private String name;

	@Getter
	private String description;

	private SpellType(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@JsonValue
	public String getName() {
		return name;
	}

}
