package com.curd.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.curd.model.entity.Notes;
import com.curd.model.entity.UserPrincipal;
import com.curd.model.entity.Users;
import com.curd.model.request.NotesRequest;
import com.curd.model.response.NotesDto;
import com.curd.repository.NotesRepository;
import com.curd.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
@SpringBootTest
class NotesServiceTest {

	@Mock
	private NotesRepository notesRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private NotesService notesService;

	private Users user;

	private UserPrincipal userPrincipal;
	
	private NotesRequest request;
	
	private Notes notes;
	private Notes notes1;


	private NotesDto expectedNotesDto;
	private NotesDto expectedNotesDto1;

	
	@BeforeEach
	void setUp() {

		MockitoAnnotations.openMocks(this);

		SecurityContext securityContext = mock(SecurityContext.class);

		Authentication authentication = mock(Authentication.class);

		userPrincipal = mock(UserPrincipal.class);

		when(userPrincipal.getUsername()).thenReturn("monu@gmail.com");

		when(authentication.getPrincipal()).thenReturn(userPrincipal);

		when(securityContext.getAuthentication()).thenReturn(authentication);

		SecurityContextHolder.setContext(securityContext);
		
		user=Users.builder()
				.id(1)
				.email("monu@gmail.com")
				.build();
		
		 request =  NotesRequest.builder()
				.title("Test Title 1")
				.description("Test Description 1")
				.build();
		 
			notes=Notes.builder()
					.id(1)
					.title("Test Title 1")
					.description("Test Description 1")
					.addedDate(LocalDate.now())
					.updatedDate(LocalDate.now())
					.user(user)
					.build();
			
			notes1=Notes.builder()
					.id(1)
					.title("Test Title 2")
					.description("Test Description 2")
					.addedDate(LocalDate.now())
					.updatedDate(LocalDate.now())
					.user(user)
					.build();
			
			expectedNotesDto=NotesDto.builder()
					.id(1)
					.title("Test Title 1")
					.description("Test Description 1")
					.addedDate(LocalDate.now())
					.updatedDate(LocalDate.now())
					.build();
			
			expectedNotesDto1=NotesDto.builder()
					.id(1)
					.title("Test Title 2")
					.description("Test Description 2")
					.addedDate(LocalDate.now())
					.updatedDate(LocalDate.now())
					.build();
			
	}

	
	
	
	@Test
	void testCreateNotes() {

		when(userRepository.findByEmail(userPrincipal.getUsername())).thenReturn(user);

		when(notesRepository.save(any(Notes.class))).thenReturn(notes);

		NotesDto actualNotesDto = notesService.createNotes(request);

		assertNotNull(actualNotesDto);
		assertEquals(expectedNotesDto.getId(), actualNotesDto.getId());
		assertEquals(expectedNotesDto.getTitle(), actualNotesDto.getTitle());
		assertEquals(expectedNotesDto.getDescription(), actualNotesDto.getDescription());
		assertEquals(expectedNotesDto.getAddedDate(), actualNotesDto.getAddedDate());
		assertEquals(expectedNotesDto.getUpdatedDate(), actualNotesDto.getUpdatedDate());

	}

	@Test
	void testGetAllNotes() {

		when(notesRepository.findAll()).thenReturn(List.of(notes, notes1));

		List<NotesDto> actualNotesDtos = notesService.getAllNotes();

		assertNotNull(actualNotesDtos);
		assertEquals(expectedNotesDto.getId(), actualNotesDtos.get(0).getId());
		assertEquals(expectedNotesDto1.getId(), actualNotesDtos.get(1).getId());
		assertEquals(expectedNotesDto.getTitle(), actualNotesDtos.get(0).getTitle());
		assertEquals(expectedNotesDto1.getTitle(), actualNotesDtos.get(1).getTitle());
		assertEquals(expectedNotesDto.getDescription(), actualNotesDtos.get(0).getDescription());
		assertEquals(expectedNotesDto1.getDescription(), actualNotesDtos.get(1).getDescription());
		assertEquals(expectedNotesDto.getAddedDate(), actualNotesDtos.get(0).getAddedDate());
		assertEquals(expectedNotesDto1.getAddedDate(), actualNotesDtos.get(1).getAddedDate());
		assertEquals(expectedNotesDto.getUpdatedDate(), actualNotesDtos.get(0).getUpdatedDate());
		assertEquals(expectedNotesDto1.getUpdatedDate(), actualNotesDtos.get(1).getUpdatedDate());

	}

	@Test
	void testGetNotesById() {
		
		when(notesRepository.findById(1)).thenReturn(Optional.of(notes));
		
		NotesDto actualNotesDto=notesService.getNotesById(1);
		
		assertNotNull(actualNotesDto);
		assertEquals(expectedNotesDto.getId(), actualNotesDto.getId());
		assertEquals(expectedNotesDto.getTitle(), actualNotesDto.getTitle());
		assertEquals(expectedNotesDto.getDescription(), actualNotesDto.getDescription());
		assertEquals(expectedNotesDto.getAddedDate(), actualNotesDto.getAddedDate());
		assertEquals(expectedNotesDto.getUpdatedDate(), actualNotesDto.getUpdatedDate());
	}

	@Test
	void testUpdateNotes() {
	
		when(notesRepository.findById(1)).thenReturn(Optional.of(notes1));
		
		when(userRepository.findByEmail(userPrincipal.getUsername())).thenReturn(user);
			
		when(notesRepository.save(any(Notes.class))).thenReturn(notes);
		
		NotesDto actualNotesDto=notesService.updateNotes(1, request);
		
		assertNotNull(actualNotesDto);
		assertEquals(expectedNotesDto.getId(), actualNotesDto.getId());
		assertEquals(expectedNotesDto.getTitle(), actualNotesDto.getTitle());
		assertEquals(expectedNotesDto.getDescription(), actualNotesDto.getDescription());
		assertEquals(expectedNotesDto.getAddedDate(), actualNotesDto.getAddedDate());
		assertEquals(expectedNotesDto.getUpdatedDate(), actualNotesDto.getUpdatedDate());

	}

	@Test
	void testDeleteNotesById() {

	when(notesRepository.findById(1)).thenReturn(Optional.of(notes));
	
	NotesDto actualDeletedNotesDto=notesService.deleteById(1);
	
	verify(notesRepository,times(1)).delete(notes);
	
	assertNotNull(actualDeletedNotesDto);
	assertEquals(expectedNotesDto.getId(), actualDeletedNotesDto.getId());
	assertEquals(expectedNotesDto.getTitle(), actualDeletedNotesDto.getTitle());
	assertEquals(expectedNotesDto.getDescription(), actualDeletedNotesDto.getDescription());
	assertEquals(expectedNotesDto.getAddedDate(), actualDeletedNotesDto.getAddedDate());
	assertEquals(expectedNotesDto.getUpdatedDate(), actualDeletedNotesDto.getUpdatedDate());
	
	}
	
	@Test
	void testGetNotesByUser() {

	    when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(mock(Authentication.class));
	    when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("monu@gmail.com");


	    when(userRepository.findByEmail("monu@gmail.com")).thenReturn(user);


	    when(notesRepository.findByUserId(user.getId())).thenReturn(List.of(notes, notes1));


	    List<NotesDto> actualNotesDtos = notesService.getNotesByUser();


	    assertNotNull(actualNotesDtos);
	    assertEquals(2, actualNotesDtos.size());

	    assertEquals(expectedNotesDto.getId(), actualNotesDtos.get(0).getId());
	    assertEquals(expectedNotesDto1.getId(), actualNotesDtos.get(1).getId());

	    assertEquals(expectedNotesDto.getTitle(), actualNotesDtos.get(0).getTitle());
	    assertEquals(expectedNotesDto1.getTitle(), actualNotesDtos.get(1).getTitle());

	    assertEquals(expectedNotesDto.getDescription(), actualNotesDtos.get(0).getDescription());
	    assertEquals(expectedNotesDto1.getDescription(), actualNotesDtos.get(1).getDescription());

	    assertEquals(expectedNotesDto.getAddedDate(), actualNotesDtos.get(0).getAddedDate());
	    assertEquals(expectedNotesDto1.getAddedDate(), actualNotesDtos.get(1).getAddedDate());

	    assertEquals(expectedNotesDto.getUpdatedDate(), actualNotesDtos.get(0).getUpdatedDate());
	    assertEquals(expectedNotesDto1.getUpdatedDate(), actualNotesDtos.get(1).getUpdatedDate());
	}
	
}
