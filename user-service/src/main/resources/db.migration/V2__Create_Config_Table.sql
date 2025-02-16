-- Create `config` table
CREATE TABLE IF NOT EXISTS config (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      config_key VARCHAR(255) NOT NULL UNIQUE,
    config_value TEXT NOT NULL,
    description TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );