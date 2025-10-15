package se.lexicon.todo_app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import se.lexicon.todo_app.dto.TodoDto;
import se.lexicon.todo_app.service.TodoService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    private TodoDto todoDto;
    private final Long TEST_TODO_ID = 1L;
    private final Long TEST_PERSON_ID = 1L;
    private final String SERVER_URL = "http://localhost";

    @BeforeEach
    void setUp() {
        todoDto = TodoDto.builder()
                .id(TEST_TODO_ID)
                .title("Test Todo")
                .description("Test Description")
                .completed(false)
                .dueDate(LocalDateTime.now().plusDays(1))
                .assignedToId(TEST_PERSON_ID)
                .build();
    }

    @Test
    void getAllTodos_ShouldReturnListOfTodos() throws Exception {
        List<TodoDto> todos = Collections.singletonList(todoDto);
        when(todoService.findAll()).thenReturn(todos);

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(TEST_TODO_ID));
    }

    @Test
    void getTodoById_ShouldReturnTodo() throws Exception {
        when(todoService.findById(TEST_TODO_ID)).thenReturn(todoDto);

        mockMvc.perform(get("/api/todos/{id}", TEST_TODO_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(TEST_TODO_ID));
    }

    @Test
    void createTodo_ShouldReturnCreatedTodo() throws Exception {
        when(todoService.create(any(TodoDto.class))).thenReturn(todoDto);

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", SERVER_URL + "/api/todos/" + TEST_TODO_ID));
    }

    @Test
    void updateTodo_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(put("/api/todos/{id}", TEST_TODO_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTodo_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/todos/{id}", TEST_TODO_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void getTodosByPerson_ShouldReturnListOfTodos() throws Exception {
        List<TodoDto> todos = Collections.singletonList(todoDto);
        when(todoService.findByAssignedTo_Id(TEST_PERSON_ID)).thenReturn(todos);

        mockMvc.perform(get("/api/todos/persons/{personId}", TEST_PERSON_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(TEST_TODO_ID));
    }

    @Test
    void getTodosByStatus_ShouldReturnListOfTodos() throws Exception {
        List<TodoDto> todos = Collections.singletonList(todoDto);
        when(todoService.findByCompleted(false)).thenReturn(todos);

        mockMvc.perform(get("/api/todos/status")
                        .param("completed", "false"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(TEST_TODO_ID));
    }

    @Test
    void getOverdueTodos_ShouldReturnListOfTodos() throws Exception {
        List<TodoDto> todos = Collections.singletonList(todoDto);
        when(todoService.findOverdueTodos()).thenReturn(todos);

        mockMvc.perform(get("/api/todos/overdue"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(TEST_TODO_ID));
    }
}