package se.lexicon.todo_app.service;

import org.springframework.stereotype.Service;
import se.lexicon.todo_app.dto.TodoDto;
import se.lexicon.todo_app.entity.Todo;
import se.lexicon.todo_app.repo.TodoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }


    @Override
    public List<TodoDto> findAll() {
        return todoRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public TodoDto findById(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Todo Found with that ID. 👀"));
        return convertToDto(todo);
    }

    @Override
    public TodoDto create(TodoDto todoDto) {
        return null; // TODO: Implement this method
    }

    @Override
    public void update(Long id, TodoDto todoDto) {
        // TODO: Implement this method
    }

    private TodoDto convertToDto(Todo todo) {
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

    @Override
    public void delete(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public List<TodoDto> findByAssignedToId(Long assignedToId) {
        return todoRepository.findByAssignedTo_Id(assignedToId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoDto> findByCompleted(boolean completed) {
        return todoRepository.findByCompleted(completed).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoDto> findOverdueTodos() {
        return todoRepository.findByDueDateBeforeAndCompletedFalse(LocalDateTime.now()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
