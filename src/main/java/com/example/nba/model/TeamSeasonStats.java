package com.example.nba.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Represents the aggregated statistics of an NBA team for a specific season.
 * Each instance contains key metrics such as total games played and averages for points,
 * rebounds, assists, steals, blocks, fouls, and turnovers, computed across all games in the season.
 *
 * The statistics are updated incrementally as new game data becomes available.
 *
 * Validation constraints ensure that all values are valid and non-negative.
 * For specific fields:
 * - team: Must be non-null.
 * - season: Must be a non-blank string.
 * - totalGamesPlayed, average fields (points, rebounds, assists, steals, blocks, fouls, turnovers): Must be zero or positive.
 */
public record TeamSeasonStats(
        @NotNull(message = "Team is required")
        Team team,

        @NotBlank(message = "Season identifier is required")
        String season,

        @PositiveOrZero
        int totalGamesPlayed,

        @PositiveOrZero
        double averageTeamPoints,

        @PositiveOrZero
        double averageTeamRebounds,

        @PositiveOrZero
        double averageTeamAssists,

        @PositiveOrZero
        double averageTeamSteals,

        @PositiveOrZero
        double averageTeamBlocks,

        @PositiveOrZero
        double averageTeamFouls,

        @PositiveOrZero
        double averageTeamTurnovers
) {
    /**
     * Creates initial team season stats from the first game stats
     */
    public static TeamSeasonStats fromFirstGame(PlayerGameStats gameStats, Team team) {
        return new TeamSeasonStats(
                team,
                gameStats.season(),
                1,
                gameStats.points(),
                gameStats.rebounds(),
                gameStats.assists(),
                gameStats.steals(),
                gameStats.blocks(),
                gameStats.fouls(),
                gameStats.turnovers()
        );
    }

    /**
     * Updates team season stats with new game statistics
     */
    public TeamSeasonStats withNewGame(PlayerGameStats gameStats) {
        if (!this.team.id().equals(gameStats.teamId())) {
            throw new IllegalArgumentException("Game stats team ID does not match season stats team ID");
        }
        if (!this.season.equals(gameStats.season())) {
            throw new IllegalArgumentException("Game stats season does not match season stats season");
        }

        int newTotalGames = this.totalGamesPlayed + 1;

        return new TeamSeasonStats(
                this.team,
                this.season,
                newTotalGames,
                updateAverage(this.averageTeamPoints, gameStats.points(), newTotalGames),
                updateAverage(this.averageTeamRebounds, gameStats.rebounds(), newTotalGames),
                updateAverage(this.averageTeamAssists, gameStats.assists(), newTotalGames),
                updateAverage(this.averageTeamSteals, gameStats.steals(), newTotalGames),
                updateAverage(this.averageTeamBlocks, gameStats.blocks(), newTotalGames),
                updateAverage(this.averageTeamFouls, gameStats.fouls(), newTotalGames),
                updateAverage(this.averageTeamTurnovers, gameStats.turnovers(), newTotalGames)
        );
    }

    private double updateAverage(double currentAverage, double newValue, int newTotalGames) {
        return ((currentAverage * (newTotalGames - 1)) + newValue) / newTotalGames;
    }
}