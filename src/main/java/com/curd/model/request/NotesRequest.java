package com.curd.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotesRequest {

	@NotBlank(message = "Title can not be blank")
	@Schema(description = "Note title",example = "Go Delhi")
	private String title;

	@NotBlank(message = "Description can not be blank")
	@Schema(description = "Note description",example = "Go delhi to buy books")
	private String description;

}
