package org.labcabrera.rolemaster.core.table.critical;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.model.combat.CriticalSeverity;
import org.labcabrera.rolemaster.core.model.combat.CriticalTableResult;
import org.labcabrera.rolemaster.core.model.tactical.DebufStatus;
import org.springframework.stereotype.Component;

@Component
public class CriticalTableS {

	public CriticalTableResult getResult(CriticalSeverity severity, Integer roll) {
		switch (severity) {
		case C:
			return getResultC(roll);
		case A:
		case B:
		case D:
		case E:
		default:
			throw new NotImplementedException();
		}
	}

	private CriticalTableResult getResultC(Integer roll) {
		if (roll >= 1 && roll <= 5) {
			return CriticalTableResult.builder()
				.text("Buen golpe. Mejor recuperación. Inténtalo de nuevo.")
				.hp(1)
				.build();
		}
		else if (roll >= 6 && roll <= 10) {
			return CriticalTableResult.builder()
				.text("Golpeas a tu enemigo con más fuerza que con intención.")
				.hp(3)
				.build();
		}
		else if (roll >= 11 && roll <= 15) {
			return CriticalTableResult.builder()
				.text("Un golpe en el costado del enemigo que te proporciona la iniciativa del siguiente asalto.")
				.hp(6)
				.specialEffect(true)
				.build();
		}
		else if (roll >= 16 && roll <= 20) {
			return CriticalTableResult.builder()
				.text("Tu ataque alcanza a tu enemigo en el costado, obligándole a retroceder 1.5 metros.")
				.hp(4)
				.specialEffect(true)
				.build()
				.addDebuf(DebufStatus.MUST_PARRY)
				.addPenalty(-20);
		}
		else if (roll >= 21 && roll <= 35) {
			return CriticalTableResult.builder()
				.text(
					"Rompes una costilla de tu enemigo con un ataque rapidísimo dirigido a su pecho. Se recupera rápidamente. Su escudo aún está encarado hacia ti.")
				.hp(3)
				.build()
				.addDebuf(DebufStatus.STUNNED);
		}
		else if (roll >= 36 && roll <= 45) {
			return CriticalTableResult.builder()
				.text("El golpe no hace más que abrir una pequeña herida en tu enemigo.")
				.hp(2)
				.bleeding(2)
				.build();

		}
		else if (roll >= 46 && roll <= 50) {
			return CriticalTableResult.builder()
				.text("Golpe en la espalda. Intenta girarse por lo que tu arma abre más aún la herida. Tu enemigo aúlla.")
				.hp(3)
				.bleeding(1)
				.build()
				.addDebuf(DebufStatus.STUNNED)
				.addDebuf(DebufStatus.CANT_PARRY);
		}
		else if (roll >= 51 && roll <= 55) {
			return CriticalTableResult.builder()
				.text("El golpe impacta con fuerza en el pecho de tu enemigo. La herida no es mortal.")
				.hp(4)
				.bleeding(2)
				.build()
				.addDebuf(DebufStatus.MUST_PARRY)
				.addPenalty(-10);
		}
		else if (roll >= 56 && roll <= 60) {
			return CriticalTableResult.builder()
				.text("Ataque hacia el costado que finalmente golpea en el muslo de tu enemigo.")
				.hp(5)
				.bleeding(2)
				.build()
				.addDebuf(DebufStatus.STUNNED);
		}
		else if (roll >= 60 && roll <= 65) {
			return CriticalTableResult.builder()
				.text("Alcanzas el antebrazo de tu enemigo, haciéndole un gran corte su su brazo.")
				.hp(4)
				.bleeding(3)
				.build()
				.addDebuf(DebufStatus.STUNNED)
				.addPenalty(-10);
		}
		else if (roll == 66) {
			return CriticalTableResult.builder()
				.text("Tu estocada se queda corta ya que tu enemigo retrocede. Rompes la rodilla de tu enemigo. Cae.")
				.hp(6)
				.build()
				.addDebuf(DebufStatus.CANT_PARRY, 3)
				.addDebuf(DebufStatus.DOWNED)
				.addPenalty(-90);
		}
		else if (roll >= 67 && roll <= 70) {
			return CriticalTableResult.builder()
				.text("Tajo en el cuello que corta cualquier ropaje y armadura que tuviese.")
				.hp(8)
				.bonus(10)
				.build()
				.addDebuf(DebufStatus.STUNNED, 4)
				.addDebuf(DebufStatus.CANT_PARRY, 2);
		}
		else if (roll >= 71 && roll <= 75) {
			return CriticalTableResult.builder()
				.text(
					"Cortas los músculos y tendones de la parte baja de la pierna de tu enemigo. Tu enemigo se tambalea hacia tu con la guardia baja.")
				.hp(7)
				.build()
				.addDebuf(DebufStatus.STUNNED, 2)
				.addDebuf(DebufStatus.CANT_PARRY, 2)
				.addPenalty(-45);
		}
		else if (roll >= 76 && roll <= 80) {
			return CriticalTableResult.builder()
				.text(
					"Golpeas alto y rápido. Cortas músculos y tendondes del brazo del escudo de tu enemigo dejando el brazo inútil.")
				.hp(9)
				.bleeding(4)
				.build()
				.addDebuf(DebufStatus.STUNNED, 6);
		}
		else if (roll >= 81 && roll <= 85) {
			return CriticalTableResult.builder()
				.text(
					"Haces honor a tu entrenamiento, alargando el arco de la estocada. El golpe impacta en el costado de tu enemigo.")
				.hp(8)
				.bleeding(4)
				.build()
				.addDebuf(DebufStatus.STUNNED, 2)
				.addDebuf(DebufStatus.CANT_PARRY, 2)
				.addPenalty(-20);
		}
		else if (roll >= 86 && roll <= 90) {
			return CriticalTableResult.builder()
				.text(
					"Impacto en la espalda que rompe algunos huesos. Tu enemigo se tambalea hacia adelante antes de caer. Tiene graves problemas para ponerse de pie.")
				.hp(9)
				.build()
				.addDebuf(DebufStatus.STUNNED, 4)
				.addDebuf(DebufStatus.CANT_PARRY, 4)
				.addDebuf(DebufStatus.DOWNED, 1)
				.addPenalty(-10);
		}
		else if (roll >= 91 && roll <= 95) {
			return CriticalTableResult.builder()
				.text(
					"Tajo en la parte alta de la pierna que la amputa. El enemigo cae inmediatamente y muere después de 6 asaltos debido al shock y la hemorragia.")
				.hp(20)
				.deathAfterRounds(6)
				.build()
				.addDebuf(DebufStatus.SHOCK, 6);
		}
		else if (roll >= 96 && roll <= 99) {
			return CriticalTableResult.builder()
				.text(
					"Partes en dos el escudo y el brazo de ty enemigo que intenta recoger su brazo amputado mientras cae. Entra en shock durante 12 asaltos y muere.")
				.hp(18)
				.deathAfterRounds(12)
				.build()
				.addDebuf(DebufStatus.CANT_PARRY, 6)
				.addDebuf(DebufStatus.STUNNED, 6);
		}
		else if (roll == 100) {
			return CriticalTableResult.builder()
				.text(
					"Golpe en plena cabeza que destruye los ojos de tu enemigo. Se revuelva sobre su espalda de dolor.")
				.hp(5)
				.build()
				.addDebuf(DebufStatus.CANT_PARRY, 30)
				.addDebuf(DebufStatus.STUNNED, 30);

		}
		throw new NotImplementedException();
	}
}
