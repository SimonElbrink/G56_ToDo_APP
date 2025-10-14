package se.lexicon.todo_app.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@Schema(description = "Data Transfer Object for Person entities")
public record PersonDto(
        @Schema(description = "Unique identifier of the person", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
        Long id,

        @Schema(description = "Full name of the person", example = "John Doe", required = true)
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,

        @Schema(description = "Email address of the person", example = "john.doe@example.com", required = true)
        @NotBlank(message = "Email is required")
        @Email(message = "Email must be a valid email address")
        @Size(max = 150, message = "Email must be less than 150 characters")
        String email,

        /**
         * Indicates that the 'createdAt' field should only be included in the JSON representation
         * if it is not null.
         * If 'createdAt' is null, Jackson will omit it from the generated JSON.
         * This helps to produce cleaner JSON output by not including properties with null values.
         */
        @Schema(description = "Date when the person was created", example = "2024-01-20", accessMode = Schema.AccessMode.READ_ONLY)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDate createdAt
) {
}