package com.LifeTracker.demo;

//import com.LifeTracker.demo.controller.AppUserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import com.LifeTracker.demo.model.AppUser;

@SpringBootTest
@AutoConfigureMockMvc
public class AppUserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCreateAppUser() throws Exception {
        String userJson = "{\"name\":\"Juan\",\"email\":\"juan@mail.com\",\"passwordHash\":\"1234\"}";
        mockMvc.perform(post("/api/appusers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(userJson))
            .andExpect(status().isCreated());
    }
}
