package se.lexicon.todo_app.dto;


import lombok.Builder;

@Builder
public record PersonDto(
        Long id,
        String name,
        String email
) {
}
