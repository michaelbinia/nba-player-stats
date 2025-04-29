package com.example.nba.model;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents the season statistics of a player in a basketball league.
 * <p>
 * This class is used to aggregate and calculate a player's seasonal performance metrics
 * based on game statistics provided throughout the season. It stores both cumulative
 * statistics (e.g., total points) and average statistics (e.g., average points per game).
 * The record is immutable and ensures data integrity through validation in its compact constructor.
 * <p>
 * Validation rules:
 * - playerId: Must be non-null.
 * - season: Must be non-blank.
 * - totalMinutesPlayed: Must be non-null.
 * - avgMinutesPlayed: Must be non-null.
 * <p>
 * The class also provides utility methods to create a new instance from the first game or
 * update the statistics by incorporating a new game.
 */
public record PlayerSeasonStats(
        @NotNull(message = "Player ID is required")
        String playerId,

        @NotBlank(message = "Season identifier is required")
        String season,

        @PositiveOrZero
        int gamesPlayed,

        @PositiveOrZero
        int totalPoints,

        @PositiveOrZero
        int totalRebounds,

        @PositiveOrZero
        int totalAssists,

        @PositiveOrZero
        int totalSteals,

        @PositiveOrZero
        int totalBlocks,

        @PositiveOrZero
        int totalFouls,

        @PositiveOrZero
        int totalTurnovers,

        @NotNull
        BigDecimal totalMinutesPlayed,

        double avgPoints,
        double avgRebounds,
        double avgAssists,
        double avgSteals,
        double avgBlocks,
        double avgFouls,
        double avgTurnovers,

        @NotNull
        BigDecimal avgMinutesPlayed
) {
    // Compact constructor for validation
    public PlayerSeasonStats {
        Objects.requireNonNull(playerId, "Player ID cannot be null");
        Objects.requireNonNull(season, "Season cannot be null");
        Objects.requireNonNull(totalMinutesPlayed, "Total minutes played cannot be null");
        Objects.requireNonNull(avgMinutesPlayed, "Average minutes played cannot be null");
    }

    // Static factory method to create from first game
    public static PlayerSeasonStats fromFirstGame(PlayerGameStats gameStats) {
        return new PlayerSeasonStats(
                gameStats.playerId(),
                gameStats.season(),
                1,
                gameStats.points(),
                gameStats.rebounds(),
                gameStats.assists(),
                gameStats.steals(),
                gameStats.blocks(),
                gameStats.fouls(),
                gameStats.turnovers(),
                gameStats.minutesPlayed(),
                gameStats.points(),
                gameStats.rebounds(),
                gameStats.assists(),
                gameStats.steals(),
                gameStats.blocks(),
                gameStats.fouls(),
                gameStats.turnovers(),
                gameStats.minutesPlayed()
        );
    }

    // Method to create new instance with updated stats
    public PlayerSeasonStats withNewGame(PlayerGameStats gameStats) {
        if (!gameStats.playerId().equals(this.playerId)) {
            throw new IllegalArgumentException("Game stats player ID doesn't match season stats player ID");
        }
        if (!gameStats.season().equals(this.season)) {
            throw new IllegalArgumentException("Game stats season doesn't match season stats season");
        }

        int newGamesPlayed = this.gamesPlayed + 1;

        // Calculate new totals
        int newTotalPoints = this.totalPoints + gameStats.points();
        int newTotalRebounds = this.totalRebounds + gameStats.rebounds();
        int newTotalAssists = this.totalAssists + gameStats.assists();
        int newTotalSteals = this.totalSteals + gameStats.steals();
        int newTotalBlocks = this.totalBlocks + gameStats.blocks();
        int newTotalFouls = this.totalFouls + gameStats.fouls();
        int newTotalTurnovers = this.totalTurnovers + gameStats.turnovers();
        BigDecimal newTotalMinutesPlayed = this.totalMinutesPlayed.add(gameStats.minutesPlayed());

        // Calculate new averages
        return new PlayerSeasonStats(
                this.playerId,
                this.season,
                newGamesPlayed,
                newTotalPoints,
                newTotalRebounds,
                newTotalAssists,
                newTotalSteals,
                newTotalBlocks,
                newTotalFouls,
                newTotalTurnovers,
                newTotalMinutesPlayed,
                (double) newTotalPoints / newGamesPlayed,
                (double) newTotalRebounds / newGamesPlayed,
                (double) newTotalAssists / newGamesPlayed,
                (double) newTotalSteals / newGamesPlayed,
                (double) newTotalBlocks / newGamesPlayed,
                (double) newTotalFouls / newGamesPlayed,
                (double) newTotalTurnovers / newGamesPlayed,
                newTotalMinutesPlayed.divide(BigDecimal.valueOf(newGamesPlayed), 1, java.math.RoundingMode.HALF_UP)
        );
    }
}