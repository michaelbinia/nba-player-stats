package com.example.nba.repository.key;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DefaultKeyGeneratorTest {

    @Autowired
    private DefaultKeyGenerator defaultKeyGenerator;

    @Test
    @DisplayName("Should generate a key with a single component")
    void shouldGenerateKeyWithSingleComponent() {
        // Arrange
        String component = "playerId";

        // Act
        String result = defaultKeyGenerator.generateKey(component);

        // Assert
        assertEquals("playerId", result, "Expected generated key to match the single component.");
    }

    @Test
    @DisplayName("Should generate a key with multiple components")
    void shouldGenerateKeyWithMultipleComponents() {
        // Arrange
        String component1 = "teamId";
        String component2 = "season";
        String component3 = "gameId";

        // Act
        String result = defaultKeyGenerator.generateKey(component1, component2, component3);

        // Assert
        assertEquals("teamId:season:gameId", result, "Expected generated key to correctly format multiple components.");
    }

    @Test
    @DisplayName("Should generate a key with no components")
    void shouldGenerateKeyWithNoComponents() {
        // Act
        String result = defaultKeyGenerator.generateKey();

        // Assert
        assertEquals("", result, "Expected generated key to be an empty string when no components are provided.");
    }

    @Test
    @DisplayName("Should generate a key with components that include empty strings")
    void shouldGenerateKeyWithEmptyStringComponents() {
        // Arrange
        String component1 = "player";
        String component2 = "";
        String component3 = "stats";

        // Act
        String result = defaultKeyGenerator.generateKey(component1, component2, component3);

        // Assert
        assertEquals("player::stats", result, "Expected generated key to handle empty string components correctly.");
    }
}