package org.labcabrera.rolemaster.core.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {

	private String code;

	private String message;

	@Builder.Default
	private List<ApiErrorMessage> messages = new ArrayList<>();

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class ApiErrorMessage {

		private String message;

	}

}
