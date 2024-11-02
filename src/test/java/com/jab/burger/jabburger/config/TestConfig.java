package com.jab.burger.jabburger.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.jab.burger.jabburger.services.AdminUserServiceTest;
import org.mockito.Mockito;

@Configuration
@Import({SecurityConfigTest.class})
public class TestConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public AdminUserServiceTest adminUserService() {
        return Mockito.mock(AdminUserServiceTest.class);
    }
} 