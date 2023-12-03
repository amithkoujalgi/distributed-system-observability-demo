package io.github.amithkoujalgi.demo.initializers;

import io.github.amithkoujalgi.demo.entities.User;
import io.github.amithkoujalgi.demo.repositories.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultUserInitializer implements InitializingBean {
    @Autowired
    private UserRepository userRepository;
    @Override
    public void afterPropertiesSet() throws Exception {
        // Check if the default user already exists
        if (userRepository.findByUsername("admin") == null) {
            // Create and save the default user
            User defaultUser = new User();
            defaultUser.setUsername("admin");
            userRepository.save(defaultUser);
        }
        if (userRepository.findByUsername("trader1") == null) {
            // Create and save the default user
            User defaultUser = new User();
            defaultUser.setUsername("trader1");
            userRepository.save(defaultUser);
        }
    }
}
