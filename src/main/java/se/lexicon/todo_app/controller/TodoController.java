package se.lexicon.todo_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import se.lexicon.todo_app.dto.TodoDto;
import se.lexicon.todo_app.exception.ErrorResponse;
import se.lexicon.todo_app.service.TodoService;

import java.net.URI;
import java.util.List;

@RestController

// localhost:9090/api/todos
@RequestMapping(value = "api/todos", produces = MediaType.APPLICATION_JSON_VALUE)

@Validated
@Tag(name = "Todo API", description = "API endpoints for managing todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @Operation(summary = "Create new todo", description = "Creates a new todo item")
    @ApiResponse(responseCode = "201", description = "Todo successfully created")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createTodo(
            @Parameter(description = "Todo details")
            @RequestBody
            @Valid TodoDto todoDto,
            UriComponentsBuilder uriBuilder
    ) {

        TodoDto created = todoService.create(todoDto);

        URI location = uriBuilder
                .path("/api/todos/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Update todo", description = "Updates an existing todo item")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Todo successfully updated"),
            @ApiResponse(responseCode = "404", description = "Todo not found", content = @Content())
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTodo(
            @Parameter(description = "ID of the todo to update")
            @PathVariable
            @NotNull(message = "Id cannot be null")
            Long id,
            @Parameter(description = "Updated todo details")
            @RequestBody
            @Valid
            TodoDto todoDto
    ) {
        todoService.update(id, todoDto);
    }


    @Operation(summary = "Delete todo", description = "Deletes a todo item")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Todo successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Todo not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(
            @Parameter(description = "ID of the todo to delete")
            @PathVariable
            @NotNull(message = "Id cannot be null")
            Long id
    ) {
        todoService.delete(id);
    }

    @Operation(summary = "Get all todos", description = "Retrieves a list of all todo items")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved todo list")
    @RequestMapping(method = RequestMethod.GET)
    public List<TodoDto> getAllTodos() {
        return todoService.findAll();
    }

    @Operation(summary = "Get todo by ID", description = "Retrieves a specific todo item by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved todo"),
            @ApiResponse(responseCode = "404", description = "Todo not found" , content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TodoDto getTodoById(
            @Parameter(description = "ID of the todo to retrieve")
            @PathVariable("id")
            @NotNull(message = "Id cannot be null")
            @Positive(message = "Id must be positive")
            Long id
    ) {
        return todoService.findById(id);
    }

    @Operation(summary = "Get todos by person", description = "Retrieves all todos for a specific person")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved todos")
    @GetMapping("/persons/{personId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoDto> getTodosByPerson(
            @Parameter(description = "ID of the person")
            @PathVariable
            @NotNull(message = "Person id cannot be null")
            Long personId
    ) {
        return todoService.findByAssignedToId(personId);
    }

    @Operation(summary = "Get todos by status", description = "Retrieves todos based on completion status")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved todos")
    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoDto> getTodosByStatus(
            @Parameter(description = "Completion status of todos")
            @RequestParam(required = true)
            boolean completed
    ) {
        return todoService.findByCompleted(completed);
    }

    @Operation(summary = "Get overdue todos", description = "Retrieves all overdue todo items")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved overdue todos")
    @GetMapping("/overdue")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoDto> getOverdueTodos() {
        return todoService.findOverdueTodos();
    }

}
