package se.lexicon.todo_app.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import se.lexicon.todo_app.dto.TodoDto;
import se.lexicon.todo_app.service.TodoService;

import java.util.List;

@RestController
@RequestMapping("api/todos") // localhost:9090/api/todos
public class TodoController {

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<TodoDto> getAllTodos() {

        return todoService.findAll();

    }

    // GET localhost:9090/api/todos/2
    @RequestMapping("/{id}")
    public TodoDto getTodoById(@PathVariable Long id) {

        return todoService.findById(id);
    }


}
