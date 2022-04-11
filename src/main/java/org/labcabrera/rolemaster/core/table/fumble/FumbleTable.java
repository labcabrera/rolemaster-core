package org.labcabrera.rolemaster.core.table.fumble;

import org.apache.commons.lang3.NotImplementedException;
import org.labcabrera.rolemaster.core.model.combat.FumbleTableResult;
import org.labcabrera.rolemaster.core.model.tactical.DebufStatus;
import org.labcabrera.rolemaster.core.model.tactical.actions.FumbleType;
import org.springframework.stereotype.Component;

@Component
public class FumbleTable {

	public FumbleTableResult get(FumbleType type, Integer roll) {
		switch (type) {
		case HAND_1H:
			return getWeapon1h(roll);
		default:
			throw new NotImplementedException();
		}
	}

	private FumbleTableResult getWeapon1h(Integer roll) {
		if (roll >= 1 && roll <= 25) {
			return FumbleTableResult.builder()
				.text("Tus manos están sudorosas. Deberás mejorar.")
				.build();
		}
		else if (roll >= 26 && roll <= 30) {
			return FumbleTableResult.builder()
				.text("Torpe. Dejas caer tu arma. Tardas 2 asaltos en recuperarla, a menos que desenvaines una nueva en el próximo asalto.")
				.specialResult(true)
				.build();
		}
		else if (roll >= 31 && roll <= 40) {
			return FumbleTableResult.builder()
				.text("Tu pie resbala. Pierdes la oportunidad de realizar ese ataque tan importante.")
				.build();
		}
		else if (roll >= 41 && roll <= 50) {
			return FumbleTableResult.builder()
				.text("Estás tenso, por lo que se te monta un músculo. Recibes 1d5 puntos de daño.")
				.specialResult(true)
				.build();
		}
		else if (roll >= 51 && roll <= 60) {
			return FumbleTableResult.builder()
				.text(
					"Intentas sorprender a tu enemigo con una maniobra envolvente. Eres malo. Pierdes 2 asaltos mientras intentas recuperarte.")
				.build()
				.addDebuf(DebufStatus.MUST_PARRY, 2);
		}
		else if (roll >= 61 && roll <= 65) {
			return FumbleTableResult.builder()
				.text(
					"Enganchas el arma en tus propias ropas, haciendo que salga disparada de tu mano. Pasas 2 asaltos intentando que no caiga al suelo.")
				.build()
				.addDebuf(DebufStatus.MUST_PARRY, 2);
		}
		else if (roll == 66) {
			return FumbleTableResult.builder()
				.text(
					"Ejecutas un ataque perfecto... contra tu aliado más cercano (o contra ti mismo si no hay nadie cerca). Tu aliado recibe 1d10 puntos y un critico B")
				.specialResult(true)
				.build();
		}
		else if (roll >= 67 && roll <= 70) {
			return FumbleTableResult.builder()
				.text("Tropiezas sobre una superficie imaginaria. Pasas 2 asaltos dando traspiés. Aún puedes parar.")
				.build();
		}
		else if (roll >= 71 && roll <= 80) {
			return FumbleTableResult.builder()
				.text("Te ha distraído ese maldito duende de la esquina. Pierdes los 2 asaltos de ataque siguientes.")
				.build()
				.addDebuf(DebufStatus.MUST_PARRY, 2);
		}
		else if (roll >= 81 && roll <= 85) {
			return FumbleTableResult.builder()
				.text("Te quedas son respiración de repente. Tómate 2 asaltos para relajarte.")
				.build();
		}
		else if (roll >= 86 && roll <= 90) {
			return FumbleTableResult.builder()
				.text("Por suerte aprenderás que ese bailoteo no es apropiado en combate. Aturdido 2 asaltos por tu falta de capacidad.")
				.build()
				.addDebuf(DebufStatus.STUNNED, 2);
		}
		else if (roll >= 91 && roll <= 95) {
			return FumbleTableResult.builder()
				.text("Caes al ir tu ataque demasiado lejos. Aturdido durante 3 asaltos.")
				.build()
				.addDebuf(DebufStatus.STUNNED, 3);
		}
		else if (roll >= 96 && roll <= 99) {
			return FumbleTableResult.builder()
				.text("¡La excitación es demasiado para ti! Tu frenesí momentáneo te deja aturdido y sin poder parar durante 3 asaltos.")
				.build()
				.addDebuf(DebufStatus.STUNNED, 3)
				.addDebuf(DebufStatus.CANT_PARRY, 3);
		}
		else if (roll == 100) {
			return FumbleTableResult.builder()
				.text("Intentas mutilarte. Recibes un crítico D.")
				.specialResult(true)
				.build();
		}
		throw new NotImplementedException();

	}

}
