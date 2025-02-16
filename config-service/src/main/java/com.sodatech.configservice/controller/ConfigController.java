package com.sodatech.configservice.controller;

import com.sodatech.configservice.entity.Config;
import com.sodatech.configservice.service.ConfigService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing configuration parameters.
 */
@RestController
@RequestMapping("sodatech/v1/configs")
@AllArgsConstructor
@Slf4j
public class ConfigController {

    private final ConfigService configService;

    /**
     * Creates a new configuration.
     *
     * @param config the configuration object to be created
     * @return ResponseEntity with creation status
     */
    @PostMapping
    public ResponseEntity<String> createConfig(@RequestBody Config config) {
        log.info("Creating new config: {}", config);
        configService.saveConfig(config);
        return ResponseEntity.status(HttpStatus.CREATED).body("Config created successfully!");
    }

    /**
     * Retrieves a configuration by its ID.
     *
     * @param id the ID of the configuration
     * @return ResponseEntity containing the configuration
     */
    @GetMapping("/{id}")
    public ResponseEntity<Config> getConfig(@PathVariable Long id) {
        log.info("Fetching config with ID: {}", id);
        return ResponseEntity.ok(configService.getConfigById(id));
    }

    /**
     * Retrieves all configurations.
     *
     * @return ResponseEntity containing a list of all configurations
     */
    @GetMapping
    public ResponseEntity<List<Config>> getConfig() {
        log.info("Fetching all configs");
        return ResponseEntity.ok(configService.getAllConfigs());
    }

    /**
     * Deletes a configuration by its ID.
     *
     * @param id the ID of the configuration to be deleted
     * @return ResponseEntity with no content status
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConfig(@PathVariable Long id) {
        log.info("Deleting config with ID: {}", id);
        configService.deleteConfig(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates an existing configuration.
     *
     * @param id     the ID of the configuration to update
     * @param config the updated configuration object
     * @return ResponseEntity containing the updated configuration
     */
    @PutMapping("/{id}")
    public ResponseEntity<Config> updateConfig(@PathVariable Long id, @RequestBody Config config) {
        log.info("Updating config with ID: {}", id);
        return ResponseEntity.ok(configService.updateConfig(id, config));
    }
}