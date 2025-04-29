package com.example.nba.model;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents a player in the NBA system.
 * <p>
 * A Player is uniquely identified by an ID and has a name associated with it.
 * Both fields are mandatory and cannot be blank.
 */
public record Player(
        @NotBlank(message = "Player ID is required")
        String id,

        @NotBlank(message = "Player name is required")
        String name

) {
}
