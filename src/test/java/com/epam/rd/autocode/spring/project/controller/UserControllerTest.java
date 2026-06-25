package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.UserRegistrationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
    }

    @Test
    void createEmployee_WithValidPayload_ShouldReturnSuccessMessage() throws Exception {
        String dummyJsonBody = "{\n" +
                "  \"email\": \"new_employee@test.com\",\n" +
                "  \"name\": \"John Doe\",\n" +
                "  \"password\": \"securePass123\",\n" +
                "  \"role\": \"EMPLOYEE\"\n" +
                "}";

        mockMvc.perform(post("/api/users/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dummyJsonBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Employee created successfully"));
    }
}