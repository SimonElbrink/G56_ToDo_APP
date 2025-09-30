package se.lexicon.todo_app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@RequiredArgsConstructor
@ToString

@Entity
@Table(name = "people")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @NonNull
    @Column(nullable = false, length = 100)
    private String name;

    @Setter
    @NonNull
    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @Column(nullable = false, updatable = false)
    private LocalDate createdAt;


    @PrePersist
    void onCreate() {
        this.createdAt = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person person)) return false;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(email, person.email) && Objects.equals(createdAt, person.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, createdAt);
    }
}
