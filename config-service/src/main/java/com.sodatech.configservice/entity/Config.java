package com.sodatech.configservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Entity class representing a configuration setting in the system.
 */
@Entity
@Data
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The key of the configuration setting.
     */
    @Column(name = "config_key")
    private String key;

    /**
     * The value associated with the configuration key.
     */
    private String value;

    /**
     * A brief description of the configuration setting.
     */
    private String description;

}