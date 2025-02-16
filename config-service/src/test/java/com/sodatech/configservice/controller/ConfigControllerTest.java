package com.sodatech.configservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sodatech.configservice.entity.Config;
import com.sodatech.configservice.exception.ResourceNotFoundException;
import com.sodatech.configservice.service.ConfigService;
import com.sodatech.configservice.config.SecurityConfig;
import com.sodatech.configservice.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest({ConfigController.class, ConfigService.class, JwtUtil.class, SecurityConfig.class})
@WithMockUser(username = "testUser", roles = {"ADMIN"})
class ConfigControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConfigService configService;

    @Autowired
    private ObjectMapper objectMapper;

    private Config sampleConfig;

    @BeforeEach
    void setUp() {
        sampleConfig = new Config();
        sampleConfig.setId(1L);
        sampleConfig.setKey("testKey");
        sampleConfig.setValue("testValue");
    }


    @Test
    void testCreateConfig() throws Exception {
        doNothing().when(configService).saveConfig(any(Config.class));

        mockMvc.perform(post("/sodatech/v1/configs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleConfig)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Config created successfully!"));

        verify(configService, times(1)).saveConfig(any(Config.class));
    }

    @Test
    void testGetConfigById() throws Exception {
        when(configService.getConfigById(1L)).thenReturn(sampleConfig);

        mockMvc.perform(get("/sodatech/v1/configs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleConfig.getId()))
                .andExpect(jsonPath("$.key").value(sampleConfig.getKey()))
                .andExpect(jsonPath("$.value").value(sampleConfig.getValue()));

        verify(configService, times(1)).getConfigById(1L);
    }

    @Test
    void testGetConfigById_NotFound() throws Exception {
        when(configService.getConfigById(anyLong())).thenThrow(new ResourceNotFoundException("Config not found"));

        mockMvc.perform(get("/sodatech/v1/configs/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllConfigs() throws Exception {
        List<Config> configs = Arrays.asList(sampleConfig);
        when(configService.getAllConfigs()).thenReturn(configs);

        mockMvc.perform(get("/sodatech/v1/configs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(sampleConfig.getId()))
                .andExpect(jsonPath("$[0].key").value(sampleConfig.getKey()));

        verify(configService, times(1)).getAllConfigs();
    }

    @Test
    void testDeleteConfig() throws Exception {
        doNothing().when(configService).deleteConfig(1L);

        mockMvc.perform(delete("/sodatech/v1/configs/1"))
                .andExpect(status().isNoContent());

        verify(configService, times(1)).deleteConfig(1L);
    }

    @Test
    void testDeleteConfig_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Config not found")).when(configService).deleteConfig(anyLong());

        mockMvc.perform(delete("/sodatech/v1/configs/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateConfig() throws Exception {
        when(configService.updateConfig(anyLong(), any(Config.class))).thenReturn(sampleConfig);

        mockMvc.perform(put("/sodatech/v1/configs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleConfig)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleConfig.getId()))
                .andExpect(jsonPath("$.key").value(sampleConfig.getKey()));

        verify(configService, times(1)).updateConfig(anyLong(), any(Config.class));
    }

    @Test
    void testUpdateConfig_NotFound() throws Exception {
        when(configService.updateConfig(anyLong(), any(Config.class)))
                .thenThrow(new ResourceNotFoundException("Config not found"));

        mockMvc.perform(put("/sodatech/v1/configs/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleConfig)))
                .andExpect(status().isNotFound());
    }
}
