package se.lexicon.todo_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import se.lexicon.todo_app.dto.PersonDto;
import se.lexicon.todo_app.service.PersonService;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    private PersonDto personDto;
    private final Long TEST_PERSON_ID = 1L;

    @BeforeEach
    void setUp() {
        personDto = PersonDto.builder()
                .id(TEST_PERSON_ID)
                .name("Test Person")
                .email("test.person@test.com")
                .build();
    }

    @Test
    void getPerson_ShouldReturnListOfPersons() throws Exception {
        List<PersonDto> persons = Collections.singletonList(personDto);
        when(personService.findAll()).thenReturn(persons);

        mockMvc.perform(get("/api/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(TEST_PERSON_ID));
    }

    @Test
    void getPersonById_ShouldReturnPerson() throws Exception {
        when(personService.findById(TEST_PERSON_ID)).thenReturn(personDto);

        mockMvc.perform(get("/api/persons/{id}", TEST_PERSON_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(TEST_PERSON_ID));
    }

    @Test
    void createPerson_ShouldReturnCreatedPerson() throws Exception {
        when(personService.createPerson(any(PersonDto.class))).thenReturn(personDto);

        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(TEST_PERSON_ID));
    }

    @Test
    void updatePerson_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(put("/api/persons/{id}", TEST_PERSON_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deletePerson_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/persons/{id}", TEST_PERSON_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void getPersonById_WithInvalidId_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/api/persons/{id}", -1))
                .andExpect(status().isBadRequest());
    }

}