package com.sodatech.configservice.repository;

import com.sodatech.configservice.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {

    /**
     * Finds a configuration by its key.
     *
     * @param key The key of the configuration.
     * @return An Optional containing the configuration if found, otherwise empty.
     */
    Optional<Config> findByKey(String key);

    /**
     * Checks if a configuration with the given key exists.
     *
     * @param key The key of the configuration.
     * @return True if the configuration exists, otherwise false.
     */
    boolean existsByKey(String key);

    /**
     * Deletes a configuration by its key.
     *
     * @param key The key of the configuration to delete.
     */
    void deleteByKey(String key);
}