package org.labcabrera.rolemaster.core.model.i18n;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "i18Messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class I18nMessage {

	@Id
	private String id;

	private String keyId;

	private String languageId;

	private String categoryId;

	private String message;
}
