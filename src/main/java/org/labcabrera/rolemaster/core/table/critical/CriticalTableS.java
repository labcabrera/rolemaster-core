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
		case A:
			return getResultA(roll);
		case B:
			return getResultB(roll);
		case C:
			return getResultC(roll);
		case D:
			return getResultD(roll);
		case E:
			return getResultE(roll);
		default:
			throw new NotImplementedException();
		}
	}

	private CriticalTableResult getResultA(Integer roll) {
		if (roll >= 1 && roll <= 5) {
			return CriticalTableResult.builder()
				.text("Ataque débil.")
				.build();
		}
		else if (roll >= 6 && roll <= 10) {
			return CriticalTableResult.builder()
				.text("Buen movimiento aunque no haces nada.")
				.hp(1)
				.build();
		}
		else if (roll >= 11 && roll <= 15) {
			return CriticalTableResult.builder()
				.text("La hoja falla la cara de tu enemigo por centímetros. Tienes la iniciativa el asalto siguiente.")
				.hp(1)
				.hasInitiative(true)
				.build();
		}
		else if (roll >= 16 && roll <= 20) {
			return CriticalTableResult.builder()
				.text("El golpe pasa por debajo del brazo de tu enemigo por lo que no acierta de pleno. Retrocede.")
				.hp(1)
				.build()
				.addDebuf(DebufStatus.MUST_PARRY);
		}
		else if (roll >= 21 && roll <= 35) {
			return CriticalTableResult.builder()
				.text("El intento de esquiva de tu enemigo evita que adopte una posición agresiva.")
				.hp(2)
				.build()
				.addDebuf(DebufStatus.MUST_PARRY)
				.addBonus(10);
		}
		else if (roll >= 36 && roll <= 45) {
			return CriticalTableResult.builder()
				.text("Herida fina en el mislo debido al más fino de los cortes.")
				.bleeding(1)
				.build();
		}
		else if (roll >= 46 && roll <= 50) {
			return CriticalTableResult.builder()
				.text("Golpe en la espalda. Tu enemigo intenta protegerse con un mandoble al aire.")
				.hp(2)
				.build()
				.addDebuf(DebufStatus.MUST_PARRY)
				.addPenalty(-30);
		}
		else if (roll >= 51 && roll <= 55) {
			return CriticalTableResult.builder()
				.text("Golpe en el pecho. Se tambalea hacia atrás intentao defenderse de tu con una guarda bastante pésima.")
				.hp(2)
				.bleeding(1)
				.build()
				.addDebuf(DebufStatus.MUST_PARRY)
				.addPenalty(-25);
		}
		else if (roll >= 56 && roll <= 60) {
			return CriticalTableResult.builder()
				.text("Te recuperas de un mandoble inicial abriendo un corte en el muslo de tu enemigo.")
				.hp(3)
				.bleeding(2)
				.build()
				.addDebuf(DebufStatus.MUST_PARRY);
		}
		else if (roll >= 61 && roll <= 65) {
			return CriticalTableResult.builder()
				.text(
					"Finges golpear alto, asestándolo por la parte baja, abriendo una brecha en la parte posterior de la pierna de tu enemigo.")
				.hp(3)
				.bleeding(2)
				.build()
				.addPenalty(-10);
		}
		else if (roll == 66) {
			return CriticalTableResult.builder()
				.text(
					"Tu enemigo bloquea el ataque con su brazo del escudo. Se rompe el hombro y deja inútil el brazo. Tienes la iniciativa.")
				.hp(9)
				.bleeding(2)
				.build()
				.addDebuf(DebufStatus.STUNNED, 3)
				.addBonus(10)
				.addEfect("Hombro del escudo roto y brazo inútil.");
		}
		else if (roll >= 67 && roll <= 70) {
			return CriticalTableResult.builder()
				.text("El golpe impacta cerca del cuello. Tu enemigo está horrorizado.")
				.hp(6)
				.build()
				.addDebuf(DebufStatus.STUNNED, 3)
				.addDebuf(DebufStatus.CANT_PARRY, 1);
		}
		else if (roll >= 71 && roll <= 75) {
			return CriticalTableResult.builder()
				.text("Golpe en la parte baja de la pierna que corta tendones. Pobre mamón.")
				.hp(4)
				.bleeding(2)
				.build()
				.addDebuf(DebufStatus.STUNNED, 2)
				.addPenalty(-30)
				.addEfect("Tendones de la parte baja de la pierna seccionados");
		}
		else if (roll >= 76 && roll <= 80) {
			return CriticalTableResult.builder()
				.text("Tu enemigo se achacha pero aún así logras impactar en la parte superior de su brazo. Sangra como un cerdo.")
				.hp(5)
				.bleeding(3)
				.build()
				.addDebuf(DebufStatus.STUNNED, 2)
				.addDebuf(DebufStatus.CANT_PARRY, 2)
				.addPenalty(-25);
		}
		else if (roll >= 81 && roll <= 85) {
			return CriticalTableResult.builder()
				.text("Tu enemigo se aparta a la derecha ante tu ataque. Le haces una herida grave.")
				.hp(6)
				.bleeding(6)
				.build()
				.addDebuf(DebufStatus.STUNNED, 5)
				.addBonus(20);
		}
		else if (roll >= 86 && roll <= 90) {
			return CriticalTableResult.builder()
				.text("Tu enemigo intenta alejarse de tu arremetida pero aún así alcanzas su costado.")
				.hp(8)
				.bleeding(2)
				.build()
				.addDebuf(DebufStatus.STUNNED)
				.addDebuf(DebufStatus.CANT_PARRY)
				.addPenalty(-10);
		}
		else if (roll >= 91 && roll <= 95) {
			return CriticalTableResult.builder()
				//TODO conditional effect
				.text("Tu enemigo intenta alejarse de tu arremetida pero aún así alcanzas su costado. ESPECIAL: DEPENDE DE SI LLEVA YELMO")
				.hp(3)
				.specialEffect(true)
				.build();
		}
		else if (roll >= 96 && roll <= 99) {
			return CriticalTableResult.builder()
				.text("La punta de tu arma corta la nariz de tu enemigo. Herida leve y cicatriz permanente.")
				.hp(2)
				.bleeding(2)
				.build()
				.addDebuf(DebufStatus.STUNNED, 6)
				.addPenalty(-30)
				.addEfect("Corte en la nariz con cicatriz permanente");
		}
		else if (roll == 100) {
			return CriticalTableResult.builder()
				.text("El golpe secciona la arteria carotida y la vena yugular, rompiendo el cuello. Muere después de 6 asaltos de agonia.")
				.deathAfterRounds(6)
				.build()
				.addDebuf(DebufStatus.SHOCK, 6)
				.addEfect("Arteria y vena del cuello seccionada");
		}
		throw new NotImplementedException();
	}

	private CriticalTableResult getResultB(Integer roll) {
		if (roll >= 1 && roll <= 5) {
			return CriticalTableResult.builder()
				.text("Golpe flojo que falla claramente.")
				.build();
		}
		else if (roll >= 6 && roll <= 10) {
			return CriticalTableResult.builder()
				.text("Golpe fuerte con la hoja. Tu enemigo retrocede claramente antes de que le ensartes.")
				.hp(2)
				.build();
		}
		else if (roll >= 11 && roll <= 15) {
			return CriticalTableResult.builder()
				.text("El enemigo se pone fuera de tu alcance. Tienes la iniciativa del asalto siguiente.")
				.hp(3)
				.hasInitiative(true)
				.build();
		}
		else if (roll >= 16 && roll <= 20) {
			return CriticalTableResult.builder()
				.text("Impacto en el costado. El enemigo se defiende con energía.")
				.hp(2)
				.build()
				.addDebuf(DebufStatus.MUST_PARRY, 1)
				.addPenalty(-10);
		}
		else if (roll >= 21 && roll <= 35) {
			return CriticalTableResult.builder()
				.text("Tu enemigo se tambalea cuando tu ataque golpea su costado. Su posición defensiva parece bastante torpe.")
				.hp(2)
				.build()
				.addDebuf(DebufStatus.MUST_PARRY, 1)
				.addPenalty(-20);
		}
		else if (roll >= 36 && roll <= 45) {
			return CriticalTableResult.builder()
				.text("Golpeas en la espinilla. Si no tiene grebas le abres una brecha en la espinilla.")
				.hp(2)
				//TODO depende grebas
				.specialEffect(true)
				.build();
		}
		else if (roll >= 46 && roll <= 50) {
			return CriticalTableResult.builder()
				.text("Tu enemigo gira de forma extraña para evitar tu ataque. Le golpeas en la espalda.")
				.hp(4)
				.build()
				.addDebuf(DebufStatus.MUST_PARRY)
				.addPenalty(-30);
		}
		else if (roll >= 51 && roll <= 55) {
			return CriticalTableResult.builder()
				.text("Golpe de calidad. Herida leve en el pecho. Si tiene armadura se tambalea un poco. Si no, la herida es efectiva.")
				.specialEffect(true)
				.build();
		}
		else if (roll >= 56 && roll <= 60) {
			return CriticalTableResult.builder()
				.text("El filo del arma entra en contacto con claridad. Herida leve en el muslo.")
				.hp(4)
				.bleeding(2)
				.build()
				.addDebuf(DebufStatus.MUST_PARRY, 2);
		}
		else if (roll >= 61 && roll <= 65) {
			return CriticalTableResult.builder()
				.text("Impactas en el antebrazo. La herida sangra mucho de manera sorprendente.")
				.hp(4)
				.bleeding(2)
				.build()
				.addDebuf(DebufStatus.STUNNED, 1)
				.addPenalty(-10);
		}
		else if (roll == 66) {
			return CriticalTableResult.builder()
				.text("Tu golpe falla en el torso, rompiendo el codo de tu enemigo. Deja caer el arma y tiene el brazo inútil.")
				.hp(8)
				.build()
				.addDebuf(DebufStatus.STUNNED, 4)
				.addDebuf(DebufStatus.CANT_PARRY, 2)
				.addEfect("Brazo roto");
		}
		else if (roll >= 67 && roll <= 70) {
			return CriticalTableResult.builder()
				.text("Casi consigues decapitar a ty enemigo con un golpe en el cuello. Tu enemigo no está contento.")
				.hp(7)
				.bleeding(3)
				.build()
				.addDebuf(DebufStatus.STUNNED, 2)
				.addPenalty(-5);
		}
		else if (roll >= 71 && roll <= 75) {
			return CriticalTableResult.builder()
				.text("Cortas los músculos de la pantorrilla. Tu enemigo está demasiado dolorido como para mantenerse en pie.")
				.hp(6)
				.build()
				.addDebuf(DebufStatus.STUNNED, 1)
				.addDebuf(DebufStatus.CANT_PARRY, 1)
				.addPenalty(-30)
				.addEfect("Corte en los músculos de la pantorilla");
		}
		else if (roll >= 76 && roll <= 80) {
			return CriticalTableResult.builder()
				.text("Tu enemigo mueve con demasiada lentitud su brazo del escudo. Le haces un corte en el brazo.")
				.hp(6)
				.bleeding(3)
				.build()
				.addDebuf(DebufStatus.STUNNED, 2)
				.addDebuf(DebufStatus.CANT_PARRY, 2)
				.addPenalty(-30);
		}
		else if (roll >= 81 && roll <= 85) {
			return CriticalTableResult.builder()
				.text(
					"El filo de tu arma se introduce hasta la mitad en tu enemigo, abriéndole una horrible herida. La sangre mana hacia todos lados.")
				.hp(7)
				.bleeding(6)
				.build()
				.addDebuf(DebufStatus.STUNNED, 2)
				.addDebuf(DebufStatus.CANT_PARRY, 2);
		}
		else if (roll >= 86 && roll <= 90) {
			return CriticalTableResult.builder()
				.text(
					"Golpe en la espalda. Tu enemigo cae de espaldas intentando evitar tu estocada. Se levanta mirando en la direccion equivocada.")
				.hp(10)
				.bleeding(3)
				.build()
				.addDebuf(DebufStatus.STUNNED, 3)
				.addDebuf(DebufStatus.CANT_PARRY, 3);
		}
		else if (roll >= 91 && roll <= 95) {
			return CriticalTableResult.builder()
				.text(
					"Golpe en la cadera con mucha fuerza. Tu enemigo se tambalea. Su recuperación es lenta.")
				.hp(7)
				.build()
				.addDebuf(DebufStatus.STUNNED, 3)
				.addDebuf(DebufStatus.CANT_PARRY)
				.addPenalty(-20)
				.addBonus(10);
		}
		else if (roll >= 96 && roll <= 99) {
			return CriticalTableResult.builder()
				.text(
					"Abres la cabeza de tu enemigo causándole daño masivo cerebral. Cae y myere después de 6 asaltos.")
				.hp(20)
				.deathAfterRounds(6)
				.build()
				.addDebuf(DebufStatus.SHOCK, 6);
		}
		else if (roll == 100) {
			return CriticalTableResult.builder()
				.text(
					"Destripas a tu enemigo, matándolo en el acto. 25% de probabilidades de que tu arma se enrede con las tripas durante 1 asalto.")
				.specialEffect(true)
				.instantDeath(true)
				.build();
		}
		throw new NotImplementedException();
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
				.hasInitiative(true)
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
				.addDebuf(DebufStatus.STUNNED)
				.addEfect("Costilla rota");
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
		else if (roll >= 61 && roll <= 65) {
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
				.addPenalty(-90)
				.addEfect("Rodilla rota");
		}
		else if (roll >= 67 && roll <= 70) {
			return CriticalTableResult.builder()
				.text("Tajo en el cuello que corta cualquier ropaje y armadura que tuviese.")
				.hp(8)
				.build()
				.addDebuf(DebufStatus.STUNNED, 4)
				.addDebuf(DebufStatus.CANT_PARRY, 2)
				.addBonus(10);
		}
		else if (roll >= 71 && roll <= 75) {
			return CriticalTableResult.builder()
				.text(
					"Cortas los músculos y tendones de la parte baja de la pierna de tu enemigo. Tu enemigo se tambalea hacia tu con la guardia baja.")
				.hp(7)
				.build()
				.addDebuf(DebufStatus.STUNNED, 2)
				.addDebuf(DebufStatus.CANT_PARRY, 2)
				.addPenalty(-45)
				.addEfect("Músculos y tendones de la parte baja de la pierna seccionados.");
		}
		else if (roll >= 76 && roll <= 80) {
			return CriticalTableResult.builder()
				.text(
					"Golpeas alto y rápido. Cortas músculos y tendondes del brazo del escudo de tu enemigo dejando el brazo inútil.")
				.hp(9)
				.bleeding(4)
				.build()
				.addDebuf(DebufStatus.STUNNED, 6)
				.addEfect("Músculos y tendones del brazo del escudo seccionados.");
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
				.addPenalty(-10)
				.addEfect("Huesos de la espalda rotos.");
		}
		else if (roll >= 91 && roll <= 95) {
			return CriticalTableResult.builder()
				.text(
					"Tajo en la parte alta de la pierna que la amputa. El enemigo cae inmediatamente y muere después de 6 asaltos debido al shock y la hemorragia.")
				.hp(20)
				.deathAfterRounds(6)
				.build()
				.addDebuf(DebufStatus.SHOCK, 6)
				.addEfect("Pierna amputada y muerte por shock y hemorragia.");
		}
		else if (roll >= 96 && roll <= 99) {
			return CriticalTableResult.builder()
				.text(
					"Partes en dos el escudo y el brazo de ty enemigo que intenta recoger su brazo amputado mientras cae. Entra en shock durante 12 asaltos y muere.")
				.hp(18)
				.deathAfterRounds(12)
				.build()
				.addDebuf(DebufStatus.CANT_PARRY, 6)
				.addDebuf(DebufStatus.STUNNED, 6)
				.addEfect("Brazo del escudo amputado y muerte por shock y hemorragia.");
		}
		else if (roll == 100) {
			return CriticalTableResult.builder()
				.text(
					"Golpe en plena cabeza que destruye los ojos de tu enemigo. Se revuelva sobre su espalda de dolor.")
				.hp(5)
				.build()
				.addDebuf(DebufStatus.CANT_PARRY, 30)
				.addDebuf(DebufStatus.STUNNED, 30)
				.addEfect("Ojos destrozados.");

		}
		throw new NotImplementedException();
	}

	private CriticalTableResult getResultD(Integer roll) {
		if (roll >= 1 && roll <= 5) {
			return CriticalTableResult.builder()
				.text("El ataque no tiene fuerza.")
				.hp(2)
				.build();
		}
		else if (roll >= 6 && roll <= 10) {
			return CriticalTableResult.builder()
				.text("Se descuida un momento y lo único que haces es rozarlo ligeramente.")
				.hp(4)
				.build();
		}
		else if (roll >= 11 && roll <= 15) {
			return CriticalTableResult.builder()
				.text("Fuerzas a tu enemigo a retroceder. Te mantiene a raya con mandobles desesperados al aire.")
				.hp(3)
				.build()
				.addDebuf(DebufStatus.MUST_PARRY);
		}
		else if (roll >= 16 && roll <= 20) {
			return CriticalTableResult.builder()
				.text("Aciertas, sajando el costado de tu enemigo. Tienes la iniciativa el siguiente asalto.")
				.hp(2)
				.hasInitiative(true)
				.build()
				.addPenalty(-10);
		}
		else if (roll >= 21 && roll <= 35) {
			return CriticalTableResult.builder()
				.text("Golpe al brazo y pecho. Tu enemigo no puede defenderse durante un momento. Te colocas en su lado del escudo.")
				.hp(3)
				.build()
				.addDebuf(DebufStatus.STUNNED, 1)
				.addDebuf(DebufStatus.CANT_PARRY, 1);
		}
		else if (roll >= 36 && roll <= 45) {
			return CriticalTableResult.builder()
				.text("Tu enemigo para tu ataque hacia su pecho, aunque logras golpearle en la parte superior del mismo.")
				.hp(3)
				.bleeding(2)
				.build();
		}
		else if (roll >= 46 && roll <= 50) {
			return CriticalTableResult.builder()
				.text("Alcanzas a tu enemigo en la parte baja de la espalda. Se revuelve pero se desequilibra.")
				.hp(3)
				.bleeding(2)
				.build()
				.addDebuf(DebufStatus.STUNNED, 1)
				.addDebuf(DebufStatus.CANT_PARRY, 1);

		}
		else if (roll >= 51 && roll <= 55) {
			return CriticalTableResult.builder()
				.text("Potente golpe en el torso superior. La herida es profunda. Su guardia sigue alta de manera increíble.")
				.hp(5)
				.bleeding(3)
				.build()
				.addDebuf(DebufStatus.MUST_PARRY, 1)
				.addPenalty(-15);
		}
		else if (roll >= 56 && roll <= 60) {
			return CriticalTableResult.builder()
				.text("La punta de tu espada impacta en el muslo de tu enemigo. Retuerces el arma.")
				.hp(6)
				.bleeding(2)
				.build()
				.addDebuf(DebufStatus.STUNNED, 2);
		}
		else if (roll >= 61 && roll <= 65) {
			return CriticalTableResult.builder()
				.text("Tienes suerte al golpear a tu enemigo en el antebrazo mientras se recuperaba de otra estocada.")
				.hp(4)
				.bleeding(3)
				.build()
				.addDebuf(DebufStatus.STUNNED, 2)
				.addPenalty(-10);
		}
		else if (roll == 66) {
			return CriticalTableResult.builder()
				.text("Dejas inconsciente a tu enemigo durate 6 horas con un golpe en la cabeza. Si no tiene yelmo lo matas en el acto")
				//TODO conditional effecto
				.specialEffect(true)
				.hp(15)
				.build();
		}
		else if (roll >= 67 && roll <= 70) {
			return CriticalTableResult.builder()
				.text("Golpeas a tu enemigo en el hombro, sajando músculos.")
				.hp(5)
				.build()
				.addDebuf(DebufStatus.STUNNED, 3)
				.addPenalty(-20)
				.addBonus(10);
		}
		else if (roll >= 71 && roll <= 75) {
			return CriticalTableResult.builder()
				.text(
					"Cortas los músculos y tendones de la parte baja de la pierna de tu enemigo. No podrá seguir mucho más en pue. Su guardia es débil.")
				.build()
				.addDebuf(DebufStatus.STUNNED, 3)
				.addDebuf(DebufStatus.MUST_PARRY, 2)
				.addPenalty(-50);
		}
		else if (roll >= 76 && roll <= 80) {
			return CriticalTableResult.builder()
				.text(
					"Equivocándose, tu enemigo coloca su brazo del arma en el camino de tu espada. Seccionas tendones. El brazo queda colgando e inútil.")
				.hp(10)
				.build()
				.addDebuf(DebufStatus.STUNNED, 4)
				.addDebuf(DebufStatus.MUST_PARRY, 2);
		}
		else if (roll >= 81 && roll <= 85) {
			return CriticalTableResult.builder()
				.text(
					"Introduces tu arma en el estómago de tu enemigo. Herida grave abdominal. Tu enemigo palicede rápidamente antes la pérdida de sangre.")
				.hp(10)
				.bleeding(8)
				.build()
				.addDebuf(DebufStatus.STUNNED, 4)
				.addDebuf(DebufStatus.MUST_PARRY, 2)
				.addPenalty(-10);
		}
		else if (roll >= 86 && roll <= 90) {
			return CriticalTableResult.builder()
				.text(
					"El intento de desarmar a tu enemigo es más que efectivo. Amputas la mano de tu oponente. Cae en shock durante 6 asaltos y después muere.")
				.hp(6)
				.deathAfterRounds(6)
				.build()
				.addDebuf(DebufStatus.STUNNED, 6)
				.addDebuf(DebufStatus.CANT_PARRY, 6)
				.addDebuf(DebufStatus.SHOCK, 6)
				.addEfect("Mano amputada");
		}
		else if (roll >= 91 && roll <= 95) {
			return CriticalTableResult.builder()
				.text(
					"Amputas el brazo del arma de tu enemigo enterrando la espada en el costado. Cae. Muere debido al shock después de 12 asaltos.")
				.hp(15)
				.deathAfterRounds(12)
				.build()
				.addDebuf(DebufStatus.STUNNED, 9)
				.addDebuf(DebufStatus.CANT_PARRY, 9)
				.addDebuf(DebufStatus.SHOCK, 12)
				.addEfect("Brazo amputado");
		}
		else if (roll >= 96 && roll <= 99) {
			return CriticalTableResult.builder()
				.text(
					"Corte en el costado. Muere en 3 asaltos debido a daño de órganos internos. Cae insconsciente inmediatamente.")
				.hp(20)
				.deathAfterRounds(3)
				.build()
				.addDebuf(DebufStatus.UNCONSCIOUS, 3)
				.addEfect("Corte mortal en el costado");
		}
		else if (roll == 100) {
			return CriticalTableResult.builder()
				.text(
					"Impalas a tu enemigo en el corazón, destruyéndolo. Muere en el acto. 25% de probabilidades de que el arma quede atrapada durante 2 asaltos.")
				.hp(12)
				.instantDeath(true)
				.specialEffect(true)
				.build()
				.addEfect("Corazón destruido");
		}
		throw new NotImplementedException();
	}

	private CriticalTableResult getResultE(Integer roll) {
		if (roll >= 1 && roll <= 5) {
			return CriticalTableResult.builder()
				.text("Tu ataque es débil.")
				.hp(3)
				.build();
		}
		else if (roll >= 6 && roll <= 10) {
			return CriticalTableResult.builder()
				.text("Desequilibras a tu enemigo. Obtienes la iniciativa del siguiente asalto.")
				.hp(5)
				.hasInitiative(true)
				.build();
		}
		else if (roll >= 11 && roll <= 15) {
			return CriticalTableResult.builder()
				.text("Desvías el arma de tu enemigo, obligándole a retroceder.")
				.hp(4)
				.build()
				.addDebuf(DebufStatus.MUST_PARRY, 1);
		}
		else if (roll >= 16 && roll <= 20) {
			return CriticalTableResult.builder()
				.text("Fuerte golpe en las costillas. Tu enemigo baja la guardia y casi deja caer el arma.")
				.build()
				.addDebuf(DebufStatus.STUNNED, 1)
				.addDebuf(DebufStatus.CANT_PARRY, 1)
				.addBonus(10);
		}
		else if (roll >= 21 && roll <= 35) {
			return CriticalTableResult.builder()
				.text(
					"Tu enemigo evita tu esfuerzo, pero le haces una muesca en cuanto te recuperas. Recibe una herida leve en el costado y retrocede 3 metros.")
				.hp(3)
				.bleeding(1)
				.build()
				.addPenalty(-10);
		}
		else if (roll >= 36 && roll <= 45) {
			return CriticalTableResult.builder()
				.text("Golpe en la parte superior de la pierna. La armadura ayuda a parar el impacto.")
				//TODO con grebas
				.specialEffect(true)
				.build();
		}
		else if (roll >= 46 && roll <= 50) {
			return CriticalTableResult.builder()
				.text("Golpe en el estómago. Se dobla de dolor por lo que tiras de tu espada haciéndole otro tajo.")
				.hp(4)
				.bleeding(3)
				.build()
				.addDebuf(DebufStatus.STUNNED, 1)
				.addDebuf(DebufStatus.CANT_PARRY, 1);
		}
		else if (roll >= 51 && roll <= 55) {
			return CriticalTableResult.builder()
				.text("Abres un tajo en tu enemigo con poca gracia. Estás inseguro del eéxito hasta que ves la sangre que mana del pecho.")
				.hp(6)
				.bleeding(4)
				.build()
				.addDebuf(DebufStatus.STUNNED, 2)
				.addPenalty(-10);

		}
		else if (roll >= 56 && roll <= 60) {
			return CriticalTableResult.builder()
				.text("Herida en el muslo. Tu ataque penetra bastante, seccionando una vena.")
				.hp(6)
				.bleeding(4)
				.build()
				.addDebuf(DebufStatus.STUNNED, 2)
				.addPenalty(-10);
		}
		else if (roll >= 61 && roll <= 65) {
			return CriticalTableResult.builder()
				.text("Tu enemigo intenta desarmarte, pagando con una fea herida en su antebrazo.")
				.hp(6)
				.bleeding(3)
				.build()
				.addDebuf(DebufStatus.STUNNED, 2)
				.addPenalty(-15);
		}
		else if (roll == 66) {
			return CriticalTableResult.builder()
				.text(
					"Bloqueas el brazo del arma de tu enemigo y después lo seccionas. El enemigo cae inmediatamente y expira en 12 asaltos. ¡Buen golpe!")
				.hp(12)
				.deathAfterRounds(12)
				.build()
				.addDebuf(DebufStatus.SHOCK, 12)
				.addBonus(10);
		}
		else if (roll >= 67 && roll <= 70) {
			return CriticalTableResult.builder()
				.text(
					"Cortas los tendones y machacas los huesos del hombro de tu enemigo. El brazo queda inutil.")
				.bleeding(2)
				.build()
				.addDebuf(DebufStatus.STUNNED, 4)
				.addEfect("Tendones seccionados y huesos del hombro rotos. Brazo inútil.");
		}
		else if (roll >= 71 && roll <= 75) {
			return CriticalTableResult.builder()
				.text(
					"Cortas músculos y tendones de la parte baja de la pierna de tu enemigo. Tu enemigo caerá si no tiene donde apoyarse.")
				.hp(8)
				.build()
				.addDebuf(DebufStatus.STUNNED, 6)
				.addPenalty(-70);
		}
		else if (roll >= 76 && roll <= 80) {
			return CriticalTableResult.builder()
				.text(
					"Tu enemigo se acerca a tu para parar tu golpe. Le amputas dos dedos y rompes el brazo del escudo, dejándolo inútil.")
				.hp(12)
				.build()
				.addDebuf(DebufStatus.STUNNED, 3)
				.addDebuf(DebufStatus.CANT_PARRY, 3);
		}
		else if (roll >= 81 && roll <= 85) {
			return CriticalTableResult.builder()
				.text("Amputas la mano de tu oponente. Cae en shock durante 12 asaltos, momento en el que muere.")
				.hp(5)
				.deathAfterRounds(12)
				.build()
				.addDebuf(DebufStatus.STUNNED, 12)
				.addDebuf(DebufStatus.CANT_PARRY, 12)
				.addDebuf(DebufStatus.SHOCK, 12);

		}
		else if (roll >= 86 && roll <= 90) {
			return CriticalTableResult.builder()
				.text(
					"Tajo de carnicero que amputa una pierna de tu enemigo. Cae inconsciente. Muere después de 9 asaltos.")
				.hp(12)
				.deathAfterRounds(9)
				.build()
				.addDebuf(DebufStatus.UNCONSCIOUS, 9);
		}
		else if (roll >= 91 && roll <= 95) {
			return CriticalTableResult.builder()
				.text("Seccionas la espina dorsal de tu enemigo. Cae paralítico de cuello para abajo permanentemente.")
				.specialEffect(true)
				.hp(20)
				.build()
				.addDebuf(DebufStatus.PRONE, 1000)
				.addEfect("Espina dorsal seccionada. Paralitico de cuello para abajo.");
		}
		else if (roll >= 96 && roll <= 99) {
			return CriticalTableResult.builder()
				.text("Golpe en la cabeza que destruye el cerebro y hace dificil vivir al pobre diablo. Expira en el acto.")
				.instantDeath(true)
				.build()
				.addEfect("Cerebro destruido");
		}
		else if (roll == 100) {
			return CriticalTableResult.builder()
				.text(
					"¡Inmejorable! Golpe en la ingle. Todos los órganos vitales quedan destruídos inmediatamente. El enemigo muere después de 24 asaltos de pura agonía.")
				.hp(10)
				.deathAfterRounds(12)
				.build()
				.addDebuf(DebufStatus.STUNNED, 12)
				.addDebuf(DebufStatus.CANT_PARRY, 12)
				.addDebuf(DebufStatus.SHOCK, 24)
				.addEfect("Todos los órganos internos destruídos después de un golpe en la ingle.");
		}
		throw new NotImplementedException();
	}
}
