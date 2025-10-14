package se.lexicon.todo_app.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Builder
@Schema(description = "Data Transfer Object for Todo items")
public record TodoDto(
        @Schema(description = "Unique identifier of the todo item", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
        Long id,

        @Schema(description = "Title of the todo item", example = "Complete project presentation", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank(message = "Title is required")
        @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
        String title,

        @Schema(description = "Detailed description of the todo item", example = "Prepare and finalize the project presentation slides")
        @Size(max = 500, message = "Description must be less than 500 characters")
        String description,

        @Schema(description = "Indicates whether the todo item is completed (optional when creating todo)", example = "false", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        boolean completed,

        @Schema(description = "Timestamp when the todo item was created", example = "2024-01-20T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
        LocalDateTime createdAt,

        @Schema(description = "Due date and time for the todo item", example = "2024-02-01T17:00:00")
        LocalDateTime dueDate,

        @Schema(description = "ID of the person assigned to this todo item", example = "1")
        Long assignedToId,

        @Schema(description = "Number of attachments associated with this todo item", example = "2", accessMode = Schema.AccessMode.READ_ONLY)
        int numberOfAttachments
) {
}
