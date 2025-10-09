package se.lexicon.todo_app.service;

import se.lexicon.todo_app.dto.TodoDto;

import java.util.List;

public interface TodoService {

    List<TodoDto> findAll();

    TodoDto findById(Long id);

    TodoDto create(TodoDto todoDto);

    void update(Long id, TodoDto todoDto);

    void delete(Long id);

    List<TodoDto> findByAssignedToId(Long assignedToId);

    List<TodoDto> findByCompleted(boolean completed);

    List<TodoDto> findOverdueTodos();
}
