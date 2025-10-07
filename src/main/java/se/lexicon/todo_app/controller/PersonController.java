package se.lexicon.todo_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import se.lexicon.todo_app.entity.Person;
import se.lexicon.todo_app.repo.PersonRepository;
import se.lexicon.todo_app.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("api/persons")
// http://localhost:9090/api/persons
public class PersonController {

    private final PersonService personService;
    private final PersonRepository personRepository;

    public PersonController(PersonService personService, PersonRepository personRepository) {
        this.personService = personService;
        this.personRepository = personRepository;
    }


    // GET localhost:9090/api/persons
//    @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    public List<Person> getAllPersons() {
        System.out.println("Find all persons");
        return personRepository.findAll();
    }


    // POST localhost:9090/api/persons -> with a JSON Body
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createPerson(@RequestBody Person person) {
        personService.createPerson(person);
    }
}
