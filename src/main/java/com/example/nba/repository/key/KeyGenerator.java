package com.example.nba.repository.key;

/**
 * Interface for generating unique keys from a set of components.
 * <p>
 * Implementations of this interface are responsible for providing a mechanism
 * to generate keys that ensure uniqueness and can be used in contexts such as
 * caching, repository identifiers, or any other scenario requiring composite keys.
 * The specific algorithm or format for the key generation depends on the implementation.
 */
public interface KeyGenerator {
    /**
     * Generates a unique key based on the provided components.
     * <p>
     * This method combines the input components to form a single string key. The
     * specific format of the generated key depends on the implementation.
     *
     * @param components the components to be combined to form the key; must not be null,
     *                   and should include the necessary parts required for creating the key
     * @return a unique string key generated from the provided components
     */
    String generateKey(String... components);
}

