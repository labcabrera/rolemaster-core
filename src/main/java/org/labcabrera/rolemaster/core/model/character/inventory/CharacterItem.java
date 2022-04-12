package org.labcabrera.rolemaster.core.model.character.inventory;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CharacterItem {

	@Id
	private String id;

	private String itemId;

	private ItemStatus status;

	private BigDecimal weigth;

}
