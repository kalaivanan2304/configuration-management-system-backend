package com.sodatech.configservice.service;

import com.sodatech.configservice.entity.Config;
import com.sodatech.configservice.repository.ConfigRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing configuration parameters.
 */
@Service
@Slf4j
@AllArgsConstructor
public class ConfigService {

    private final ConfigRepository configRepository;

    /**
     * Saves a configuration parameter to the database.
     *
     * @param config The configuration to save.
     * @return The saved configuration.
     */
    public void saveConfig(Config config) {
        log.info("Saving configuration: {}", config);
        configRepository.save(config);
    }

    /**
     * Retrieves a configuration parameter by its ID.
     *
     * @param id The ID of the configuration.
     * @return The configuration if found, otherwise null.
     */
    public Config getConfigById(Long id) {
        log.info("Fetching configuration by ID: {}", id);
        Optional<Config> config = configRepository.findById(id);
        return config.orElse(null);
    }

    /**
     * Retrieves all configuration parameters.
     *
     * @return A list of all configurations.
     */
    public List<Config> getAllConfigs() {
        log.info("Fetching all configurations");
        return configRepository.findAll();
    }

    /**
     * Updates an existing configuration parameter.
     *
     * @param id The ID of the configuration to update.
     * @param config Config parameters to be updated
     * @return The updated configuration if found, otherwise null.
     */
    public Config updateConfig(Long id, Config config) {
        log.info("Updating configuration with ID: {}", id);
        Optional<Config> existingConfig = configRepository.findById(id);
        if (existingConfig.isPresent()) {
            Config updatedConfig = existingConfig.get();
            updatedConfig.setKey(config.getKey());
            updatedConfig.setValue(config.getValue());
            updatedConfig.setDescription(config.getDescription());
            log.info("Configuration updated successfully: {}", updatedConfig);
            return configRepository.save(updatedConfig);
        }
        log.warn("Configuration with ID: {} not found", id);
        return null;
    }

    /**
     * Deletes a configuration parameter by its ID.
     *
     * @param id The ID of the configuration to delete.
     */
    public void deleteConfig(Long id) {
        log.info("Deleting configuration with ID: {}", id);
        configRepository.deleteById(id);
    }
}