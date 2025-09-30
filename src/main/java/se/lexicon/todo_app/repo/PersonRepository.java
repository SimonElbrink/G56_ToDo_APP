package se.lexicon.todo_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.lexicon.todo_app.entity.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByEmailIgnoreCase(String email);

    @Query("select (count(p) > 0) from Person p where upper(p.email) = upper(:email)")
    boolean existsByEmailIgnoreCase(@Param("email") String email);

    long deleteByEmailIgnoreCase(String email);


}
