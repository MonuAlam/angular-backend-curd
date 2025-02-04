package com.curd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@RestController
@RequestMapping("/notes")
public class NotesController {

	@Autowired
	private NotesService notesService;

	@PostMapping
	public NotesDto createNotes(@RequestBody NotesRequest request) {

		return notesService.createNotes(request);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getall")
	public List<NotesDto> getAllNotes() {

		return notesService.getAllNotes();
	}

	@GetMapping("/{id}")
	public NotesDto getNotesById(@PathVariable Integer id) {

		return notesService.getNotesById(id);
	}

	@PutMapping("/{id}")
	public NotesDto updateNotes(@PathVariable Integer id, @RequestBody NotesRequest request) {

		return notesService.updateNotes(id, request);
	}

	@DeleteMapping("/{id}")
	public NotesDto deleteById(@PathVariable Integer id) {

		return notesService.deleteById(id);
	} 
//get notes of specific user by authheader
	
	@GetMapping("/user")
	public List<NotesDto> getNotesByUser() {

		return notesService.getNotesByUser();
	}
	
}
