package se.lexicon.todo_app.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PersonDto(
        Long id,

        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        String name,

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
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDate createdAt
) {
}
