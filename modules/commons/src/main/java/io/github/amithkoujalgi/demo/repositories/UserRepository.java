package io.github.amithkoujalgi.demo.repositories;

import io.github.amithkoujalgi.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findAllById(Long userId);
}
