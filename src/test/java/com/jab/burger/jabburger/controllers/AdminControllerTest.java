package com.jab.burger.jabburger.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jab.burger.jabburger.models.TestUser;
import com.jab.burger.jabburger.services.AdminUserServiceTest;
import com.jab.burger.jabburger.config.SecurityConfigTest;

@WebMvcTest(AdminController.class)
@Import(SecurityConfigTest.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminUserServiceTest adminUserService;

    @Autowired
    private ObjectMapper objectMapper;

    private TestUser testUser;

    @BeforeEach
    void setUp() {
        testUser = new TestUser();
        testUser.setId(1L);
        testUser.setNombre("Juan");
        testUser.setApellido("Pérez");
        testUser.setCelular("987654321");
        testUser.setDni("12345678");
        testUser.setEmail("juan.perez@test.com");
        testUser.setPassword("password123");
        testUser.setRol("ADMIN");
    }

    @Test
    void createUser_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        TestUser invalidUser = new TestUser();
        invalidUser.setNombre(""); // Nombre vacío para forzar error

        mockMvc.perform(post("/admin/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidUser)))
                .andExpect(status().isBadRequest());
    }
}
