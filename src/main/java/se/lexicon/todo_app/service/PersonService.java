package se.lexicon.todo_app.service;

import se.lexicon.todo_app.dto.PersonDto;

import java.util.List;

public interface PersonService {

    PersonDto createPerson(PersonDto person);

    PersonDto findById(Long id);

    List<PersonDto> findAll();
}
