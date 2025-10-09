package se.lexicon.todo_app.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,
        String[] errors,
        LocalDateTime timestamp
) {
}
