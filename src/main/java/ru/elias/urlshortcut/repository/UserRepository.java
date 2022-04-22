package ru.elias.urlshortcut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.elias.urlshortcut.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

    boolean existsByUsername(String username);

}
