package se.lexicon.todo_app.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.lexicon.todo_app.entity.Person;
import se.lexicon.todo_app.entity.Todo;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TestEntityManager entityManager;

    private Person testPerson;

    @BeforeEach
    void setUp() {
        testPerson = new Person("John", "john@test.com");
        testPerson = entityManager.persistFlushFind(testPerson);

        LocalDateTime now = LocalDateTime.now();

        Todo todo1 = new Todo("Shopping", "Buy groceries", now.plusDays(1));
        Todo todo2 = new Todo("Reading", "Read book: Java for Beginners", now.plusDays(5));
        todo2.setAssignedTo(testPerson); // assign to test person
        Todo todo3 = new Todo("Studying", "Study for exam", now.plusDays(3));
        Todo todo4 = new Todo("Go To Gym", "Workout session"); // No due date
        Todo todo5 = new Todo("Clean bike", "Maintenance", now.plusHours(12));
        todo5.setAssignedTo(testPerson); // assign to test person

        Todo todo6 = new Todo("Clean Car", "Wash and vacuum", now.plusDays(1));
        todo6.setCompleted(true);

        todoRepository.saveAll(Arrays.asList(todo1, todo2, todo3, todo4, todo5, todo6));

    }

    @Test
    @DisplayName("Find Todos containing title (substring) returns matching todos, in a case-insensitive manner")
    void findByTitleContainsIgnoreCase_ShouldReturnMatchingTodos() {
        List<Todo> foundTodos = todoRepository.findByTitleContainsIgnoreCase("ing");

        assertEquals(3, foundTodos.size());
        assertTrue(foundTodos.stream().anyMatch(todo -> todo.getTitle().equalsIgnoreCase("Shopping")));
        assertTrue(foundTodos.stream().anyMatch(todo -> todo.getTitle().equalsIgnoreCase("Reading")));
        assertTrue(foundTodos.stream().anyMatch(todo -> todo.getTitle().equalsIgnoreCase("Studying")));
    }

    @Test
    @DisplayName("Find Todos by Person ID should return that person's Todos")
    void findByPerson_Id_ShouldReturnPersonsTodos() {

        long id = testPerson.getId();
        List<Todo> foundTodosByPersonID = todoRepository.findByAssignedTo_Id(id);
        assertEquals(2, foundTodosByPersonID.size());
        foundTodosByPersonID.forEach(todo -> assertEquals(id, todo.getAssignedTo().getId()));


    }
}