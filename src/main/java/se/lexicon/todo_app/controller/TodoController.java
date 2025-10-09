package se.lexicon.todo_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.lexicon.todo_app.dto.TodoDto;
import se.lexicon.todo_app.service.TodoService;

import java.util.List;

@RestController
@RequestMapping("api/todo") // localhost:9090/api/todo
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
    public TodoDto createTodo(@RequestBody TodoDto todoDto) {
        return todoService.create(todoDto);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTodo(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        todoService.update(id, todoDto);
    }

    // GET localhost:9090/api/todo/2
    @RequestMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TodoDto getTodoById(@PathVariable Long id) {
        return todoService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable Long id) {
        todoService.delete(id);
    }

    @GetMapping("/person/{personId}")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoDto> getTodosByPerson(@PathVariable Long personId) {
        return todoService.findByAssignedToId(personId);
    }

    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoDto> getTodosByStatus(@RequestParam boolean completed) {
        return todoService.findByCompleted(completed);
    }

    @GetMapping("/overdue")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoDto> getOverdueTodos() {
        return todoService.findOverdueTodos();
    }

}
