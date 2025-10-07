package se.lexicon.todo_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.lexicon.todo_app.dto.PersonDto;
import se.lexicon.todo_app.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("api/person")
// http://localhost:9090/api/person
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    // GET localhost:9090/api/person
//    @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDto> getAllPersons() {
        return personService.findAll();
    }

    // GET localhost:9090/api/person/:id -> with ex 1 or 2 as ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDto getPersonById(@PathVariable("id") Long id) {
        return personService.findById(id);
    }

    // POST localhost:9090/api/person -> with a JSON Body
//    @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto createPerson(@RequestBody PersonDto personDto) {
        return personService.createPerson(personDto);
    }
}
