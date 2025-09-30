package se.lexicon.todo_app.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean completed = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person assigned;


    @Transient // transient annotation is used to indicate that a field should not be persisted in the database.
    private Boolean isAssigned;

    public Todo(String title, String description, LocalDateTime dueDate, Person assigned) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.assigned = assigned;
    }

    public Todo(String title, String description, LocalDateTime dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Todo todo)) return false;
        return completed == todo.completed && Objects.equals(id, todo.id) && Objects.equals(title, todo.title) && Objects.equals(description, todo.description) && Objects.equals(createdAt, todo.createdAt) && Objects.equals(updatedAt, todo.updatedAt) && Objects.equals(dueDate, todo.dueDate) && Objects.equals(assigned, todo.assigned);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, completed, createdAt, updatedAt, dueDate, assigned);
    }
}
