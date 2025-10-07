package se.lexicon.todo_app.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PersonDto(
        Long id,
        String name,
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
