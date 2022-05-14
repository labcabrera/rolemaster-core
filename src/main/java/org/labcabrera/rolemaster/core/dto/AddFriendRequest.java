package org.labcabrera.rolemaster.core.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddFriendRequest {

	@NotBlank
	@Schema(description = "Username.", required = true, example = "alice")
	private String username;

	@Schema(description = "Message.", required = false, example = "Accept me :)")
	private String message;

}
