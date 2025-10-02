package se.lexicon.todo_app.service;

import se.lexicon.todo_app.dto.TodoDto;

import java.util.List;

public interface TodoService {

    List<TodoDto> findAll();

    TodoDto findById(Long id);

}
