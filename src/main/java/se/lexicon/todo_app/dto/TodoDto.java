package se.lexicon.todo_app.dto;


import java.time.LocalDateTime;

public record TodoDto(
        Long id,
        String title,
        String description,
        boolean completed,
        LocalDateTime createdAt,
        LocalDateTime dueDate,
        Long assignedToId,
        int numberOfAttachments
) {
}
