package org.labcabrera.rolemaster.core.service.tactical.impl.attack;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labcabrera.rolemaster.core.model.OpenRoll;
import org.labcabrera.rolemaster.core.model.tactical.TacticalCharacter;
import org.labcabrera.rolemaster.core.model.tactical.actions.TacticalActionMeleeAttack;
import org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.MeleeAttackServiceWeaponTableProcessor;
import org.labcabrera.rolemaster.core.table.weapon.WeaponTable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MeleeAttackServiceWeaponTableProcessorTest {

	@InjectMocks
	private MeleeAttackServiceWeaponTableProcessor processor;

	@Mock
	private WeaponTable weaponTable;

	@Mock
	private TacticalAttackContext ctx;

	@Mock
	private TacticalActionMeleeAttack tacticalActionMeleeAttack;

	@Mock
	private TacticalCharacter targetCharacter;

	@Mock
	private OpenRoll roll;

	@BeforeEach
	void setUp() {
		when(ctx.getAction()).thenReturn(tacticalActionMeleeAttack);
		when(ctx.getTarget()).thenReturn(targetCharacter);
		when(tacticalActionMeleeAttack.getOffensiveBonus()).thenReturn(100);
		when(tacticalActionMeleeAttack.getDefensiveBonus()).thenReturn(20);
		when(tacticalActionMeleeAttack.getWeaponId()).thenReturn("weapon-01");
		when(tacticalActionMeleeAttack.getRoll()).thenReturn(roll);
		when(targetCharacter.getArmorType()).thenReturn(1);
		when(roll.getResult()).thenReturn(56);
	}

	@Test
	void testNoCrit() {
		when(weaponTable.get(any(), any(), any())).thenReturn("15");
		processor.apply(ctx);
	}

	@Test
	void testCrit() {
		when(weaponTable.get(any(), any(), any())).thenReturn("15DS");
		processor.apply(ctx);
	}

}
