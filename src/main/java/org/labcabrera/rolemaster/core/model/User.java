package org.labcabrera.rolemaster.core.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	@Id
	private String id;

	@Builder.Default
	private List<String> friends = new ArrayList<>();

	@Builder.Default
	private List<String> blockedUsers = new ArrayList<>();

	private String defaultUniverseId;

	private Double defaultScale;

	private LocalDateTime created;

}
