package se.lexicon.todo_app.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import se.lexicon.todo_app.dto.TodoDto;
import se.lexicon.todo_app.service.TodoService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/todos") // localhost:9090/api/todos
@Validated
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<TodoDto> getAllTodos() {
        return todoService.findAll();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> createTodo(
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


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTodo(
            @PathVariable
            @NotNull(message = "Id cannot be null")
            Long id,
            @RequestBody @Valid
            TodoDto todoDto
    ) {
        todoService.update(id, todoDto);
    }

    // GET localhost:9090/api/todos/2
    @RequestMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TodoDto getTodoById(
            @PathVariable("id")
            @NotNull(message = "Id cannot be null")
            @Positive(message = "Id must be positive")
            Long id
    ) {
        return todoService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(
            @PathVariable @NotNull(message = "Id cannot be null")
            Long id
    ) {
        todoService.delete(id);
    }

    @GetMapping("/persons/{personId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoDto> getTodosByPerson(
            @PathVariable
            @NotNull(message = "Person id cannot be null")
            Long personId
    ) {
        return todoService.findByAssignedToId(personId);
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoDto> getTodosByStatus(@RequestParam(required = true) boolean completed) {
        return todoService.findByCompleted(completed);
    }

    @GetMapping("/overdue")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoDto> getOverdueTodos() {
        return todoService.findOverdueTodos();
    }

}
