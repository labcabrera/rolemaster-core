package org.labcabrera.rolemaster.core.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendRequest {

	@Id
	private String id;

	private String user;

	private String friendId;

	private String message;

	private LocalDateTime creation;

	private boolean accepted = false;

}
