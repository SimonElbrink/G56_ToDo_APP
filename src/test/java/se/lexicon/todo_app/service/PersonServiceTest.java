
package se.lexicon.todo_app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import se.lexicon.notify.model.Email;
import se.lexicon.notify.service.MessageService;
import se.lexicon.todo_app.dto.PersonDto;
import se.lexicon.todo_app.entity.Person;
import se.lexicon.todo_app.repo.PersonRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private MessageService<Email> emailService;

    @InjectMocks
    private PersonServiceImpl personService;

    private Person person;
    private PersonDto personDto;
    private final Long TEST_ID = 1L;
    private final String TEST_NAME = "Test Person";
    private final String TEST_EMAIL = "test.person@example.com";

    @BeforeEach
    void setUp() {
        person = new Person(TEST_ID, TEST_NAME, TEST_EMAIL, null);
        personDto = PersonDto.builder().id(TEST_ID).name(TEST_NAME).email(TEST_EMAIL).build();
    }

    @Test
    void testCreate() {
        // Arrange
        Person personToSave = new Person(TEST_NAME, TEST_EMAIL);
        when(personRepository.save(any(Person.class))).thenReturn(person);

        // Act
        PersonDto created = personService.createPerson(personDto);

        // Assert
        assertNotNull(created);
        assertEquals(TEST_NAME, created.name());
        assertEquals(TEST_EMAIL, created.email());
        verify(personRepository).save(any(Person.class));
        verify(emailService).sendMessage(any(Email.class));
    }

    @Test
    void testFindAll() {
        // Arrange
        Person person2 = new Person(2L, "Test Person", "test.person@example.com", null);
        when(personRepository.findAll()).thenReturn(Arrays.asList(person, person2));

        // Act
        List<PersonDto> result = personService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(TEST_NAME, result.get(0).name());
        assertEquals("Test Person", result.get(1).name());
        verify(personRepository).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        when(personRepository.findById(TEST_ID)).thenReturn(Optional.of(person));

        // Act
        PersonDto found = personService.findById(TEST_ID);

        // Assert
        assertNotNull(found);
        assertEquals(TEST_ID, found.id());
        assertEquals(TEST_NAME, found.name());
        verify(personRepository).findById(TEST_ID);
    }

    @Test
    void testFindById_NotFound() {
        // Arrange
        when(personRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> personService.findById(TEST_ID));
        verify(personRepository).findById(TEST_ID);
    }

    @Test
    void testUpdate() {
        // Arrange
        String updatedName = "Updated Test Person";
        String updatedEmail = "updated.test@example.com";
        PersonDto updateDto = PersonDto.builder().id(TEST_ID).name(updatedName).email(updatedEmail).build();
        Person updatedPerson = new Person(TEST_ID, updatedName, updatedEmail, null);

        when(personRepository.findById(TEST_ID)).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(updatedPerson);

        // Act
        PersonDto result = personService.update(TEST_ID, updateDto);

        // Assert
        assertNotNull(result);
        assertEquals(updatedName, result.name());
        assertEquals(updatedEmail, result.email());
        verify(personRepository).findById(TEST_ID);
        verify(personRepository).save(any(Person.class));
    }

    @Test
    void testDelete() {
        // Act
        personService.delete(TEST_ID);

        // Assert
        verify(personRepository).deleteById(TEST_ID);
    }

    @Test
    void testFindByEmail() {
        // Arrange
        when(personRepository.findByEmailIgnoreCase(TEST_EMAIL)).thenReturn(Optional.of(person));

        // Act
        PersonDto found = personService.findByEmail(TEST_EMAIL);

        // Assert
        assertNotNull(found);
        assertEquals(TEST_EMAIL, found.email());
        verify(personRepository).findByEmailIgnoreCase(TEST_EMAIL);
    }

    @Test
    void testFindByEmail_NotFound() {
        // Arrange
        when(personRepository.findByEmailIgnoreCase(TEST_EMAIL)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> personService.findByEmail(TEST_EMAIL));
        verify(personRepository).findByEmailIgnoreCase(TEST_EMAIL);
    }
}