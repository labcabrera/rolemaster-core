package org.labcabrera.rolemaster.core.model.character;

import java.util.ArrayList;
import java.util.List;

import org.labcabrera.rolemaster.core.model.EntityMetadata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Session {

	private String id;

	private String name;

	@Builder.Default
	private List<String> characterStatus = new ArrayList<>();

	private EntityMetadata metadata;

	@Builder.Default
	private List<String> authorization = new ArrayList<>();

}
