package io.github.amithkoujalgi.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.amithkoujalgi.demo.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {io.github.amithkoujalgi.demo.DemoApp.class})
@AutoConfigureMockMvc
class UserWorkerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getAdminUser() throws Exception {
        String uri = "/api/user/all";

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get(uri)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        User[] users = objectMapper.readValue(content, User[].class);

        assertNotNull(users);
        assertTrue(users.length > 0);
        assertTrue(users[0].getId() > 0);
        assertEquals("admin", users[0].getUsername());
    }
}