package se.lexicon.todo_app.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.lexicon.todo_app.dto.PersonDto;
import se.lexicon.todo_app.entity.Attachment;
import se.lexicon.todo_app.entity.Person;
import se.lexicon.todo_app.entity.Todo;
import se.lexicon.todo_app.repo.PersonRepository;
import se.lexicon.todo_app.repo.TodoRepository;
import se.lexicon.todo_app.service.PersonService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner run(PersonRepository personRepository, TodoRepository todoRepository, PersonService personService) {
        return (args) -> {

//            seedData(personRepository, todoRepository);


//            createPersonAndSendEmail(personService, false);

        };
    }

    private static void createPersonAndSendEmail(PersonService personService, boolean proceed) {
        if (proceed){
            personService.createPerson(
                    PersonDto.builder()
                            .name("Simon")
                            .email("Simon@lexicon.se")
                            .build()
            );
        }
    }

    private static void seedData(PersonRepository personRepository, TodoRepository todoRepository) throws IOException {
        Person dev1 = personRepository.save(new Person("Dev1", "dev1@test.se"));
        Person dev2 = personRepository.save(new Person("Dev2", "dev2@test.se"));
        Person dev3 = personRepository.save(new Person("Dev3", "dev3@test.se"));


        LocalDateTime now = LocalDateTime.now();
        Todo todo1 = new Todo("Shopping", "Buy groceries", now.plusDays(1));
        Todo todo2 = new Todo("Reading", "Read book: Java for Beginners", now.plusDays(5));
        Todo todo3 = new Todo("Studying", "Study for exam", now.plusDays(3));
        Todo todo4 = new Todo("Go To Gym", "Workout session"); // No due date
        Todo todo5 = new Todo("Clean bike", "Maintenance", now.plusHours(12));
        Todo todo6 = new Todo("Clean Car", "Wash and vacuum", now.plusDays(1));

        Attachment file1 = new Attachment("unit-test-guide.pdf", "application/pdf", "Sample PDF Content".getBytes());
        file1.setTodo(todo1);

        Attachment file2 = new Attachment("ui-mockup.png", "image/png", "Sample image content".getBytes());
        todo3.addAttachment(file2);

        Path path = Paths.get("img/todo_app.png");
        byte[] imageBytes = Files.readAllBytes(path);

        Attachment file3 = new Attachment("todo_app.png", "image/png", imageBytes);
        todo2.addAttachment(file3);

        todo1.setAssignedTo(dev1);
        todo4.setAssignedTo(dev1);
        todo2.setAssignedTo(dev2);
        todo3.setAssignedTo(dev2);
        todo5.setAssignedTo(dev3);
        // todo6 not assigned to anyone

        todoRepository.saveAll(Arrays.asList(todo1, todo2, todo3, todo4, todo5, todo6));
    }

}
