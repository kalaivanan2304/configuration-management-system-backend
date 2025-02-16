package com.sodatech.configservice.service;

import com.sodatech.configservice.entity.Config;
import com.sodatech.configservice.repository.ConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfigServiceTest {

    @Mock
    private ConfigRepository configRepository;

    @InjectMocks
    private ConfigService configService;

    private Config config;

    @BeforeEach
    void setUp() {
        config = new Config();
        config.setId(1L);
        config.setKey("testKey");
        config.setValue("testValue");
        config.setDescription("Test description");
    }

    @Test
    void testSaveConfig() {
        when(configRepository.save(config)).thenReturn(config);

        configService.saveConfig(config);

        verify(configRepository, times(1)).save(config);
    }

    @Test
    void testGetConfigById() {
        when(configRepository.findById(1L)).thenReturn(Optional.of(config));

        Config retrievedConfig = configService.getConfigById(1L);

        assertNotNull(retrievedConfig);
        assertEquals(1L, retrievedConfig.getId());
        assertEquals("testKey", retrievedConfig.getKey());
        verify(configRepository, times(1)).findById(1L);
    }

    @Test
    void testGetConfigById_NotFound() {
        when(configRepository.findById(1L)).thenReturn(Optional.empty());

        Config retrievedConfig = configService.getConfigById(1L);

        assertNull(retrievedConfig);
        verify(configRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllConfigs() {
        when(configRepository.findAll()).thenReturn(List.of(config));

        List<Config> configs = configService.getAllConfigs();

        assertFalse(configs.isEmpty());
        assertEquals(1, configs.size());
        verify(configRepository, times(1)).findAll();
    }

    @Test
    void testUpdateConfig() {
        Config updatedConfig = new Config();
        updatedConfig.setKey("updatedKey");
        updatedConfig.setValue("updatedValue");
        updatedConfig.setDescription("Updated description");

        when(configRepository.findById(1L)).thenReturn(Optional.of(config));
        when(configRepository.save(any(Config.class))).thenReturn(updatedConfig);

        Config result = configService.updateConfig(1L, updatedConfig);

        assertNotNull(result);
        assertEquals("updatedKey", result.getKey());
        assertEquals("updatedValue", result.getValue());
        verify(configRepository, times(1)).findById(1L);
        verify(configRepository, times(1)).save(any(Config.class));
    }

    @Test
    void testUpdateConfig_NotFound() {
        when(configRepository.findById(1L)).thenReturn(Optional.empty());

        Config result = configService.updateConfig(1L, config);

        assertNull(result);
        verify(configRepository, times(1)).findById(1L);
        verify(configRepository, never()).save(any(Config.class));
    }

    @Test
    void testDeleteConfig() {
        doNothing().when(configRepository).deleteById(1L);

        configService.deleteConfig(1L);

        verify(configRepository, times(1)).deleteById(1L);
    }
}
