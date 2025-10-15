package se.lexicon.todo_app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.lexicon.notify.model.Email;
import se.lexicon.notify.service.MessageService;
import se.lexicon.todo_app.dto.PersonDto;
import se.lexicon.todo_app.entity.Person;
import se.lexicon.todo_app.repo.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final MessageService<Email> emailService;


    public PersonServiceImpl(PersonRepository personRepository, MessageService<Email> emailService) {
        this.personRepository = personRepository;
        this.emailService = emailService;
    }

    @Override
    public PersonDto createPerson(PersonDto personDto) {

        Person person = new Person(personDto.name(), personDto.email());


        person = personRepository.save(person);

        // Send a welcome email to the new user
        if (person.getId() != null) {
           boolean sentMessage = emailService.sendMessage(new Email(person.getEmail(),
                    "Welcome to ToDoAPP!",
                    """
                            Hello, %s
                            Thank you for signing up to our App.
                            We hope you enjoy using it. 🎉
                            """.formatted(person.getName())));

           if (!sentMessage) {
               log.error("Failed to send welcome email to: {}", person.getEmail());
           }else{
               log.info("Successfully sent welcome email to: {}",person.getEmail());
           }
        }

        return PersonDto.builder()
                .id(person.getId())
                .name(person.getName())
                .email(person.getEmail())
                .build();
    }

    @Override
    public PersonDto findById(Long id) {

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));

        return PersonDto.builder()
                .id(person.getId())
                .name(person.getName())
                .email(person.getEmail())
                .createdAt(person.getCreatedAt())
                .build();
    }

    @Override
    public List<PersonDto> findAll() {
        return personRepository.findAll().stream()
                .map(person -> PersonDto.builder()
                        .id(person.getId())
                        .name(person.getName())
                        .email(person.getEmail())
                        .build())
                .collect(Collectors.toList());

    }

    @Override
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public PersonDto update(Long id, PersonDto personDto) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.setName(personDto.name());
        person.setEmail(personDto.email());
        Person updated = personRepository.save(person);
        log.info("Updated person: {}", updated);

        return PersonDto.builder()
                .id(updated.getId())
                .name(updated.getName())
                .email(updated.getEmail())
                .build();
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
