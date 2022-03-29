package org.labcabrera.rolemaster.core.model.strategic;

import java.util.ArrayList;
import java.util.List;

import org.labcabrera.rolemaster.core.model.EntityMetadata;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "strategicSessions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StrategicSession {

	private String id;

	private String name;

	private EntityMetadata metadata;

	@Builder.Default
	private List<String> authorization = new ArrayList<>();

}
