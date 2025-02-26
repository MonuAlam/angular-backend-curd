package com.curd.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.time.LocalDate;
import java.util.List;

import com.curd.model.request.NotesRequest;
import com.curd.model.response.NotesDto;
import com.curd.service.NotesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
class NotesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotesService notesService;

    @InjectMocks
    private NotesController notesController;

    private NotesRequest request;
    
	private NotesDto expectedNotesDto;
   
    @BeforeEach
    void setUp() {
       
    	MockitoAnnotations.openMocks(this);
       
        mockMvc = MockMvcBuilders.standaloneSetup(notesController).build();

        request = NotesRequest.builder()
                .title("Test Title 1")
                .description("Test Description 1")
                .build();
        
        expectedNotesDto = NotesDto.builder()
                .id(1)
                .title("Test Title 1")
                .description("Test Description 1")
                .addedDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();
    }

    @Test
    void testCreateNotes() throws Exception {
        
    	when(notesService.createNotes(any(NotesRequest.class))).thenReturn(expectedNotesDto);

        mockMvc.perform(post("/notes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedNotesDto.getId()))
                .andExpect(jsonPath("$.title").value(expectedNotesDto.getTitle()))
                .andExpect(jsonPath("$.description").value(expectedNotesDto.getDescription()))
                .andExpect(jsonPath("$.addedDate").value(expectedNotesDto.getAddedDate().toString()))  
                .andExpect(jsonPath("$.updatedDate").value(expectedNotesDto.getUpdatedDate().toString()));  

        verify(notesService, times(1)).createNotes(any(NotesRequest.class));
    }

    @Test
    void testGetAllNotes() throws Exception {
    	
        List<NotesDto> notesList = List.of(expectedNotesDto);
        
        when(notesService.getAllNotes()).thenReturn(notesList);

        mockMvc.perform(get("/notes/getall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(expectedNotesDto.getId()))
                .andExpect(jsonPath("$[0].title").value(expectedNotesDto.getTitle()))
                .andExpect(jsonPath("$[0].description").value(expectedNotesDto.getDescription()))
                .andExpect(jsonPath("$[0].addedDate").value(expectedNotesDto.getAddedDate().toString()))
                .andExpect(jsonPath("$[0].updatedDate").value(expectedNotesDto.getUpdatedDate().toString()));  

        verify(notesService, times(1)).getAllNotes();
    }

    @Test
    void testGetNotesById() throws Exception {
       
    	when(notesService.getNotesById(1)).thenReturn(expectedNotesDto);

        mockMvc.perform(get("/notes/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(expectedNotesDto.getId()))
        .andExpect(jsonPath("$.title").value(expectedNotesDto.getTitle()))
        .andExpect(jsonPath("$.description").value(expectedNotesDto.getDescription()))
        .andExpect(jsonPath("$.addedDate").value(expectedNotesDto.getAddedDate().toString()))  
        .andExpect(jsonPath("$.updatedDate").value(expectedNotesDto.getUpdatedDate().toString()));

        verify(notesService, times(1)).getNotesById(1);
    }

    @Test
    void testUpdateNotes() throws Exception {
        
    	when(notesService.updateNotes(eq(1), any(NotesRequest.class))).thenReturn(expectedNotesDto);

        mockMvc.perform(put("/notes/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedNotesDto.getId()))
                .andExpect(jsonPath("$.title").value(expectedNotesDto.getTitle()))
                .andExpect(jsonPath("$.description").value(expectedNotesDto.getDescription()))
                .andExpect(jsonPath("$.addedDate").value(expectedNotesDto.getAddedDate().toString()))
                .andExpect(jsonPath("$.updatedDate").value(expectedNotesDto.getUpdatedDate().toString()));

        verify(notesService, times(1)).updateNotes(eq(1), any(NotesRequest.class));
    }

    @Test
    void testDeleteNotesById() throws Exception {
        when(notesService.deleteById(1)).thenReturn(expectedNotesDto);

        mockMvc.perform(delete("/notes/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedNotesDto.getId()))
                .andExpect(jsonPath("$.title").value(expectedNotesDto.getTitle()))
                .andExpect(jsonPath("$.description").value(expectedNotesDto.getDescription()))
                .andExpect(jsonPath("$.addedDate").value(expectedNotesDto.getAddedDate().toString()))
                .andExpect(jsonPath("$.updatedDate").value(expectedNotesDto.getUpdatedDate().toString()));

        verify(notesService, times(1)).deleteById(1);
    }
    
    @Test
    void testGetNotesByUser() throws Exception {
    	List<NotesDto> notesList=List.of(expectedNotesDto);
    	when(notesService.getNotesByUser()).thenReturn(notesList);
    	
    	 mockMvc.perform(get("/notes/user"))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$[0].id").value(expectedNotesDto.getId()))
         .andExpect(jsonPath("$[0].title").value(expectedNotesDto.getTitle()))
         .andExpect(jsonPath("$[0].description").value(expectedNotesDto.getDescription()))
         .andExpect(jsonPath("$[0].addedDate").value(expectedNotesDto.getAddedDate().toString()))
         .andExpect(jsonPath("$[0].updatedDate").value(expectedNotesDto.getUpdatedDate().toString()));  

 verify(notesService, times(1)).getNotesByUser();

    	
    	
    }
}
