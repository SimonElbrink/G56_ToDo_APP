package se.lexicon.todo_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.todo_app.dto.PersonDto;
import se.lexicon.todo_app.exception.ErrorResponse;
import se.lexicon.todo_app.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("api/persons")
// http://localhost:9090/api/persons

@Validated
// This annotation is used to enable validation on the controller methods.
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // GET localhost:9090/api/persons
    // @RequestMapping(method = RequestMethod.GET)
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PersonDto> getAllPersons() {
        return personService.findAll();
    }

    // GET localhost:9090/api/persons/:id -> with ex 1 or 2 as ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDto getPersonById(
            @PathVariable("id")
            @Positive(message = "Id must be a positive number")
            Long id
    ) {
        return personService.findById(id);
    }

    @Operation(summary = "Create new person", description = "Creates a new person with the provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Person successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid person data provided", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDto createPerson(
            @Parameter(description = "Person details", required = true)
            @RequestBody
            @NotNull(message = "Person cannot be null")
            @Valid
            PersonDto personDto
    ) {
        return personService.createPerson(personDto);
    }

    // GET localhost:9090/api/persons/email?email=:email
    @Operation(summary = "Find Person by Email")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Person Successfully Found"),
            @ApiResponse(responseCode = "404", description = "Person Not Found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
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
