package se.lexicon.todo_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.todo_app.dto.PersonDto;
import se.lexicon.todo_app.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("api/person")
// http://localhost:9090/api/person

@Validated
// This annotation is used to enable validation on the controller methods.
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // GET localhost:9090/api/person
    // @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDto> getAllPersons() {
        return personService.findAll();
    }

    // GET localhost:9090/api/person/:id -> with ex 1 or 2 as ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDto getPersonById(
            @PathVariable("id")
            @Positive(message = "Id must be a positive number")
            Long id
    ) {
        return personService.findById(id);
    }

    // POST localhost:9090/api/person -> with a JSON Body
    // @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto createPerson(
            @RequestBody
            @NotNull(message = "Person cannot be null")
            @Valid
            PersonDto personDto
    ) {
        return personService.createPerson(personDto);
    }

    // GET localhost:9090/api/person/email?email=:email
    @Operation(summary = "Find Person by Email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Person Successfully Found"),
            @ApiResponse(responseCode = "404", description = "Person Not Found")
    })
    @GetMapping("/email")
    public PersonDto getPersonByEmail(
            @RequestParam(value = "email")
            @NotBlank(message = "Email is required")
            @Email(message = "Email must be a valid email address")
            @Size(max = 150, message = "Email must be less than 150 characters")
            String emailAddress
    ) {
        return personService.findByEmail(emailAddress);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePerson(
            @PathVariable
            @NotNull(message = "Id cannot be null")
            Long id,
            @RequestBody
            @NotNull(message = "Person cannot be null")
            PersonDto personDto
    ) {
        personService.update(id, personDto);
    }

    @PatchMapping("/{id}")
    public void updatePerson(@PathVariable Long id, @RequestParam String name) {
        //personService.update(id,name); // TODO: implement this method
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(
            @PathVariable
            @NotNull(message = "Id cannot be null")
            Long id
    ) {
        personService.delete(id);

    }

}
