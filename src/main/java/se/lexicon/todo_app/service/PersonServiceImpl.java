package se.lexicon.todo_app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.lexicon.notify.model.Email;
import se.lexicon.todo_app.dto.PersonDto;
import se.lexicon.todo_app.entity.Person;
import se.lexicon.todo_app.repo.PersonRepository;
import se.lexicon.notify.service.EmailService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;
    private EmailService emailService;


    public PersonServiceImpl(PersonRepository personRepository, EmailService emailService) {
        this.personRepository = personRepository;
        this.emailService = emailService;
    }

    @Override
    public PersonDto createPerson(PersonDto personDto) {

        Person person = new Person(personDto.name(), personDto.email());


        person = personRepository.save(person);

//        // Send a welcome email to the new user
//        if (saved.getId() != null) {
//           boolean sentMessage = emailService.sendMessage(new Email(person.getEmail(),
//                    "Welcome to ToDoAPP!",
//                    """
//                            Hello, %s
//                            Thank you for signing up to our App.
//                            We hope you enjoy using it. 🎉
//                            """.formatted(person.getName())));
//
//           if (!sentMessage) {
//               log.error("Failed to send welcome email to: {}", person.getEmail());
//           }else{
//               log.info("Successfully sent welcome email to: {}",person.getEmail());
//           }
//        }

        return new PersonDto(person.getId(), person.getName(), person.getEmail());
    }

    @Override
    public PersonDto findById(Long id) {

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        return new PersonDto(person.getId(), person.getName(), person.getEmail());
    }

    @Override
    public List<PersonDto> findAll() {
        return personRepository.findAll().stream()
                .map(person -> new PersonDto(person.getId(), person.getName(), person.getEmail()))
                .collect(Collectors.toList());

    }

    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public void update(Long id, PersonDto personDto) {
        //TODO Implement this method
    }

    @Override
    public PersonDto findByEmail(String email) {

        Person person = personRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        return PersonDto.builder()
                .id(person.getId())
                .name(person.getName())
                .email(person.getEmail())
                .build();
    }
}
