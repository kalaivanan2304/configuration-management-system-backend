package com.sodatech.userservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sodatech.userservice.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({SecurityConfig.class})
@Import(SecurityConfig.class) // Import the SecurityConfig for testing
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser // Simulate an authenticated user
    void testProtectedEndpointForbidden() throws Exception {
        mockMvc.perform(get("/sodatech/v1/admin/configs")) // An endpoint not explicitly allowed
                .andExpect(status().isForbidden());
    }

    @Test
    void testProtectedEndpointWithoutAuthentication() throws Exception {
        mockMvc.perform(get("/sodatech/v1/admin/configs")) // Secured endpoint
                .andExpect(status().isForbidden());
    }
}
