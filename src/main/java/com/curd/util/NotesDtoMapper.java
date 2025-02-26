package com.curd.util;

import com.curd.model.entity.Notes;
import com.curd.model.response.NotesDto;

public class NotesDtoMapper {
	
	public static NotesDto toResponseDto(Notes notes) {
		
		return NotesDto.builder()
				.id(notes.getId())
				.title(notes.getTitle())
				.description(notes.getDescription())
				.addedDate(notes.getAddedDate())
				.updatedDate(notes.getUpdatedDate())
				.build();
	}

}
