package io.github.amithkoujalgi.demo.repository;

import io.github.amithkoujalgi.demo.entities.User;
import io.github.amithkoujalgi.demo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {io.github.amithkoujalgi.demo.DemoApp.class})
class UserWorkerRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findAdminUser() {
        User user = userRepository.findByUsername("admin");
        assertTrue(user.getId() > 0);
    }
}
