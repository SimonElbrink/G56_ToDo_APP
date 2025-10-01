package se.lexicon.todo_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.todo_app.entity.Todo;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    //TODO: Add more methods
    // 🔍 Find todos by title keyword (case-insensitive contains)
    List<Todo> findByTitleContainsIgnoreCase(String title);

    // 👤 Find todos by person ID
    List<Todo> findByAssignedTo_Id(Long id);

    // ✅ Find todos by completed status
    List<Todo> findByCompleted(Boolean completed);

    // 🗓️ Find todos between two due dates
    List<Todo> findByDueDateBetween(LocalDateTime startDate, LocalDateTime DueDate);

    // 🗓️ Find todos due before a specific date and not completed
    List<Todo> findByDueDateBeforeAndCompletedFalse(LocalDateTime dateTime);

    // ❌ Find unassigned todos (person is null)
    List<Todo> findByAssignedToIsNull();

    // 🔥 Find unfinished & overdue tasks (custom query)
    List<Todo> findByCompletedFalseAndDueDateBefore(LocalDateTime dateTime);

    // ✅ Find completed tasks assigned to a specific person
    List<Todo> findByAssignedTo_IdAndCompletedTrue(Long personId);

    // 📅 Find all with no due date
    List<Todo> findByDueDateIsNull();

    // 📌 Count all tasks assigned to a person
    Long countByAssignedTo_Id(Long personId);

}
