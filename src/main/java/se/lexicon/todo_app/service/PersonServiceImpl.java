package se.lexicon.todo_app.service;

import org.springframework.stereotype.Service;
import se.lexicon.notify.model.Email;
import se.lexicon.todo_app.entity.Person;
import se.lexicon.todo_app.repo.PersonRepository;
import se.lexicon.notify.service.EmailService;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepository personRepository;
    private EmailService emailService;


    public PersonServiceImpl(PersonRepository personRepository, EmailService emailService) {
        this.personRepository = personRepository;
        this.emailService = emailService;
    }

    @Override
    public Person createPerson(Person person) {

        Person saved = personRepository.save(person);

        // Send a welcome email to the new user
        if (saved.getId() != null) {
            emailService.sendMessage(new Email(person.getEmail(),
                    "Welcome to ToDo APP!",
                    """
                            Hello, %s
                            Thank you for signing up to our App.
                            We hope you enjoy using it. 🎉
                            """.formatted(person.getName())));
        }

        return saved;
    }
}
