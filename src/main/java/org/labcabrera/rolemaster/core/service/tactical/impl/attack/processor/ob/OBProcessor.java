package org.labcabrera.rolemaster.core.service.tactical.impl.attack.processor.ob;

import org.labcabrera.rolemaster.core.service.context.AttackContext;

/**
 * Offensive bonus processor.
 */
public interface OBProcessor {

	AttackContext process(AttackContext context);
}
