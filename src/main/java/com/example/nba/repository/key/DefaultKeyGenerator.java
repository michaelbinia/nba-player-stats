package com.example.nba.repository.key;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Default implementation of the {@link KeyGenerator} interface.
 * <p>
 * This class provides a mechanism for generating unique keys by joining
 * components using a predefined separator. The generated keys are intended
 * for use in contexts like caching or as composite identifiers.
 */
@Component
public class DefaultKeyGenerator implements KeyGenerator {
    private static final Logger logger = LoggerFactory.getLogger(DefaultKeyGenerator.class);
    private static final String KEY_SEPARATOR = ":";

    @Override
    public String generateKey(String... components) {
        String key = String.join(KEY_SEPARATOR, components);
        logger.debug("Generated key: {}", key);
        return key;
    }
}