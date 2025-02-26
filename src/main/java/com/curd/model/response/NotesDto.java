package com.curd.model.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotesDto {

    @Schema(description = "The unique ID of the note", example = "1")
	private Integer id;

    @Schema(description = "The title of the note", example = "Meeting Notes")
	private String title;
    
    @Schema(description = "The content of the note", example = "Discussed project timelines")
	private String description;
	
    @Schema(description = "The date the note was created", example = "2025-02-26T12:00:00")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate addedDate;
	
    @Schema(description = "The date the note was updated", example = "2025-02-28T12:00:00")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate updatedDate;
}
