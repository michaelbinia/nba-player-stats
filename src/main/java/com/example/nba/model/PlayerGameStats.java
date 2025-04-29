package com.example.nba.model;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Represents the statistics of a player for a single game.
 * All numeric values must be non-negative.
 * Special constraints:
 * - fouls: must be between 0 and 6
 * - minutesPlayed: must be between 0 and 48, in increments of 0.1
 */
public record PlayerGameStats(
        @NotNull(message = "Statistics ID is required")
        String id,

        @NotNull(message = "Player ID is required")
        String playerId,

        @NotNull(message = "Game ID is required")
        String gameId,

        @NotNull(message = "Team ID is required")
        String teamId,

        @NotNull(message = "Timestamp is required")
        LocalDateTime timestamp,

        @NotBlank(message = "Season identifier is required")
        String season,

        @PositiveOrZero(message = "Points must be zero or positive")
        int points,

        @PositiveOrZero(message = "Rebounds must be zero or positive")
        int rebounds,

        @PositiveOrZero(message = "Assists must be zero or positive")
        int assists,

        @PositiveOrZero(message = "Steals must be zero or positive")
        int steals,

        @PositiveOrZero(message = "Blocks must be zero or positive")
        int blocks,

        @Min(value = 0, message = "Fouls must be between 0 and 6")
        @Max(value = 6, message = "Fouls must be between 0 and 6")
        int fouls,

        @PositiveOrZero(message = "Turnovers must be zero or positive")
        int turnovers,

        @NotNull
        @DecimalMin(value = "0.0", message = "Minutes played must be between 0 and 48")
        @DecimalMax(value = "48.0", message = "Minutes played must be between 0 and 48")
        BigDecimal minutesPlayed
) {
    /**
     * Validates that minutes played are in increments of 0.1
     * @throws IllegalArgumentException if minutes played is not in 0.1 increments
     */
    public PlayerGameStats {
        if (minutesPlayed.remainder(new BigDecimal("0.1")).compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("Minutes played must be in increments of 0.1");
        }
    }
}