package dev.spring.jwtdemo.controller;

import dev.spring.jwtdemo.config.SecurityConfig;
import dev.spring.jwtdemo.service.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({AuthController.class, HomeController.class})
@Import({SecurityConfig.class, TokenService.class})
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void rootWhenUnauthenticatedThen401() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void rootWhenAuthenticatedThen200() throws Exception {
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/token")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "user")))
                .andExpect(status().isOk())
                .andReturn();

        String token = result.getResponse().getContentAsString();

        this.mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .header("Authorization", "Bearer " + token))
                .andExpect(MockMvcResultMatchers.content().string("Hello, user"));
    }

    @Test
    @WithMockUser
    void rootWithMockUserThen200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}