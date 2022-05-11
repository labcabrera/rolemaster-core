package org.labcabrera.rolemaster.core.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	@Id
	private String id;

	@Builder.Default
	private List<String> friends = new ArrayList<>();

	private LocalDateTime created;

}
