package io.github.amithkoujalgi.demo.repositories;

import io.github.amithkoujalgi.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findAllById(Long userId);

    default User findRandomUser() {
        List<User> allUsers = findAll();
        if (!allUsers.isEmpty()) {
            int randomIndex = (int) (Math.random() * allUsers.size());
            return allUsers.get(randomIndex);
        }
        return null; // Return null or handle the case when there are no users
    }
}
