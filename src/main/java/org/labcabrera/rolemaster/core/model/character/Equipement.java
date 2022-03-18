package org.labcabrera.rolemaster.core.model.character;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Equipement {

	@Id
	private String id;

	private String equipementId;

}
