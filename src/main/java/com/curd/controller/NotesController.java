package com.curd.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.curd.model.request.NotesRequest;
import com.curd.model.response.NotesDto;
import com.curd.service.NotesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@RestController
@RequestMapping("/notes")
@Tag(name = "Notes APIs",description = "Operation related to Notes")
public class NotesController {

	@Autowired
	private NotesService notesService;

	@PostMapping
	@Operation(summary = "Add Note", description = "Create a new note and return the created note as the response.")
    @ApiResponse(
            responseCode = "201", 
            description = "Note successfully created",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\"id\": 1, \"title\": \"Meeting Notes\", \"description\": \"Discussed project timelines\", \"addedDate\": \"2025-02-26\", \"updatedDate\": \"2025-02-26\"}")
            )
    )
    @ApiResponse(
            responseCode = "400", 
            description = "Invalid input",
            content = @Content(mediaType = "application/json")
    )	
	public NotesDto createNotes(@RequestBody NotesRequest request) {

		return notesService.createNotes(request);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getall")
    @Operation(summary = "Get All Notes", description = "Fetch the list of all notes. Accessible only by ADMIN.")
    @ApiResponse(
            responseCode = "200", 
            description = "Successfully fetched the list of notes",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "[{\"id\": 1, \"title\": \"Meeting Notes\", \"description\": \"Discussed project timelines\", \"addedDate\": \"2025-02-26\", \"updatedDate\": \"2025-02-26\"},{\"id\": 2, \"title\": \"Meeting Notes\", \"description\": \"Discussed project timelines\", \"addedDate\": \"2025-02-26\", \"updatedDate\": \"2025-02-26\"}]")
            )
    )
    @ApiResponse(
            responseCode = "403", 
            description = "Forbidden access for non-admin users",
            content = @Content(mediaType = "application/json")
    )
	public List<NotesDto> getAllNotes() {

		return notesService.getAllNotes();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get Note by ID", description = "Fetch a note by its ID.")
    @ApiResponse(
            responseCode = "200", 
            description = "Note details fetched successfully",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "[{\"id\": 1, \"title\": \"Meeting Notes\", \"description\": \"Discussed project timelines\", \"addedDate\": \"2025-02-26\", \"updatedDate\": \"2025-02-26\"}]")
            )
    )
    @ApiResponse(
            responseCode = "404", 
            description = "Note not found",
            content = @Content(mediaType = "application/json")
    )
	public NotesDto getNotesById(@PathVariable Integer id) {

		return notesService.getNotesById(id);
	}

	@PutMapping("/{id}")
    @Operation(summary = "Update Note", description = "Update an existing note by its ID.")
    @ApiResponse(
            responseCode = "200", 
            description = "Note successfully updated",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "[{\"id\": 1, \"title\": \"Meeting Notes\", \"description\": \"Discussed project timelines\", \"addedDate\": \"2025-02-26\", \"updatedDate\": \"2025-02-26\"}]")
            )
    )
    @ApiResponse(
            responseCode = "404", 
            description = "Note not found",
            content = @Content(mediaType = "application/json")
    )	
	public NotesDto updateNotes(@PathVariable Integer id, @RequestBody NotesRequest request) {

		return notesService.updateNotes(id, request);
	}

	
	
	@DeleteMapping("/{id}")
    @Operation(summary = "Delete Note", description = "Delete a note by its ID.")
    @ApiResponse(
            responseCode = "200", 
            description = "Note successfully deleted",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "[{\"id\": 1, \"title\": \"Meeting Notes\", \"description\": \"Discussed project timelines\", \"addedDate\": \"2025-02-26\", \"updatedDate\": \"2025-02-26\"}]")
            )
    )
    @ApiResponse(
            responseCode = "404", 
            description = "Note not found",
            content = @Content(mediaType = "application/json")
    )
	public NotesDto deleteById(@PathVariable Integer id) {

		return notesService.deleteById(id);
	} 
	
	@GetMapping("/user")
    @Operation(summary = "Get Notes by User", description = "Fetch notes belonging to a specific user.")
    @ApiResponse(
            responseCode = "200", 
            description = "Notes successfully fetched for the user",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "[{\"id\": 1, \"title\": \"Meeting Notes\", \"description\": \"Discussed project timelines\", \"addedDate\": \"2025-02-26\", \"updatedDate\": \"2025-02-26\"}]")
            )
    )
    @ApiResponse(
            responseCode = "403", 
            description = "Forbidden access if the user doesn't have associated notes",
            content = @Content(mediaType = "application/json")
    )
	public List<NotesDto> getNotesByUser() {

		return notesService.getNotesByUser();
	}
	
}
