package se.lexicon.todo_app.service;

import org.springframework.stereotype.Service;
import se.lexicon.todo_app.dto.TodoDto;
import se.lexicon.todo_app.entity.Person;
import se.lexicon.todo_app.entity.Todo;
import se.lexicon.todo_app.repo.PersonRepository;
import se.lexicon.todo_app.repo.TodoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;
    private PersonRepository personRepository;

    public TodoServiceImpl(TodoRepository todoRepository, PersonRepository personRepository) {
        this.todoRepository = todoRepository;
        this.personRepository = personRepository;
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

        Todo todo = new Todo(
                todoDto.title(),
                todoDto.description(),
                todoDto.dueDate()
        );

        if(todoDto.assignedToId() != null){
            Person person = personRepository.findById(todoDto.assignedToId())
                    .orElseThrow(() -> new RuntimeException("Person not Found"));
            todo.setAssignedTo(person);
        }

        Todo save = todoRepository.save(todo);

        return convertToDto(save);
    }

    @Override
    public void update(Long id, TodoDto todoDto) {

        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        existingTodo.setTitle(todoDto.title());
        existingTodo.setDescription(todoDto.description());
        existingTodo.setDueDate(todoDto.dueDate());

        if(todoDto.assignedToId() != null){
            Person person = personRepository.findById(todoDto.assignedToId())
                    .orElseThrow(() -> new RuntimeException("Person not Found"));
            existingTodo.setAssignedTo(person);
        }else{
            existingTodo.setAssignedTo(null);
        }
        Todo updatedTodo = todoRepository.save(existingTodo);

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
