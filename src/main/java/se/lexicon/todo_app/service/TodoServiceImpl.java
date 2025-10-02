package se.lexicon.todo_app.service;

import org.springframework.stereotype.Service;
import se.lexicon.todo_app.dto.TodoDto;
import se.lexicon.todo_app.entity.Todo;
import se.lexicon.todo_app.repo.TodoRepository;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }


    @Override
    public List<TodoDto> findAll() {
        return todoRepository.findAll().stream()
                .map(this::convert)
                .toList();
    }

    @Override
    public TodoDto findById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Todo Found with that ID. 👀"));
        return convert(todo);
    }

    private TodoDto convert(Todo todo) {
        return new TodoDto(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getCompleted(),
                todo.getCreatedAt(),
                todo.getDueDate(),
                todo.getAssignedTo() != null ? todo.getAssignedTo().getId() : null,
                todo.getAttachments().size());
    }
}
