package org.labcabrera.rolemaster.core.dto.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AuthorizationContext {

	//TODO here will come the autorization data
	private Object authorization;

}
