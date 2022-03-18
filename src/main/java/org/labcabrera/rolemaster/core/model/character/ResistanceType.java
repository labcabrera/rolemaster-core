package org.labcabrera.rolemaster.core.model.character;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "resistanceTypes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResistanceType {

	private String id;

	private String name;

}
