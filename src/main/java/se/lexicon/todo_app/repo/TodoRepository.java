package se.lexicon.todo_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import se.lexicon.todo_app.entity.Todo;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    //TODO: Add more methods
    // 🔍 Find todos by title keyword (case-insensitive contains)
    List<Todo> findByTitleContainsIgnoreCase(String title);

    // 👤 Find todos by person ID
    List<Todo> findByAssignedTo_Id(Long id);

    // ✅ Find todos by completed status
    // 🗓️ Find todos between two due dates
    // 🗓️ Find todos due before a specific date and not completed
    // ❌ Find unassigned todos (person is null)
    // 🔥 Find unfinished & overdue tasks (custom query)
    // ✅ Find completed tasks assigned to a specific person
    // 📅 Find all with no due date
    // 📌 Count all tasks assigned to a person

}
