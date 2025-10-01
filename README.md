# Todo Application

![todo_app.png](img/todo_app.png)

## Objective

This is a simple yet functional Todo Application built using Spring Boot and JPA. The app allows users (persons) to
manage tasks, track completion status, and monitor due dates. Tasks can be assigned to specific users or left
unassigned. The application automatically records creation and update timestamps for each task. It is designed with a
clean architecture and follows best practices in Java development.

### Class Diagram

```mermaid
classDiagram
    class Person {
        Long id
        String name
        String email
        LocalDate createdAt
    }

    class Todo {
        Long id
        String title
        String description
        Boolean completed
        LocalDateTime createdAt
        LocalDateTime updatedAt
        LocalDateTime dueDate
        Person assignedTo
        Set<Attachment> attachments
    }

    class Attachment {
        Long id
        String fileName
        String fileType
        byte[] data
    }

    Person "1" --> "0..*" Todo : assigned
    Todo "1" --> "0..*" Attachment : attachments
```


### Person

- Represents a user in the system.
- Each person has a unique email address.
- Can be assigned multiple todo tasks.

### Todo

- Represents a task in the system.
- Contains title, description, and completion status.
- Tracks creation time, updates, and due date.
- Can be assigned to a person (optional).
- Can have multiple file attachments.

# Functionalities

- PersonRepository
    - Find all persons.
    - Find person by email.
    - Save a new person.
    - Update an existing person.
    - Delete a person.
- TodoRepository
    - Find all todos.
    - Find todo by ID.
    - Save a new todo.
    - Update an existing todo.
    - Delete a todo.