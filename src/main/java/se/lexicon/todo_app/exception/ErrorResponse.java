package se.lexicon.todo_app.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Schema(description = "Response object for error handling")
public record ErrorResponse(
        @Schema(description = "HTTP status code of the error", example = "404")
        int status,
        @Schema(description = "Array of error messages", example = "[\"Resource not found\", \"title must not be empty\", \"deadline must be a future date\", \"Person with ID 1 not found\"], \"email does not follow pattern\"]")
        String[] errors,
        @Schema(description = "Timestamp when the error occurred", example = "2024-01-20T10:30:00")
        LocalDateTime timestamp
) {

    public ErrorResponse(int status, String[] errors) {
        this(status, errors, LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
    }
}
