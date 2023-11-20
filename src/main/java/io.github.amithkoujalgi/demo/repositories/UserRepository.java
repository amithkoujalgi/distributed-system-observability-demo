package io.gitub.amithkoujalgi.demo.repositories;

import io.gitub.amithkoujalgi.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
