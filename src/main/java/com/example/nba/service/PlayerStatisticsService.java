package com.example.nba.service;

import com.example.nba.model.PlayerGameStats;
import com.example.nba.model.PlayerSeasonStats;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Represents a service for managing player statistics, including game-level and season-level data.
 * The service provides functions to save, retrieve, and manage a player's statistics.
 */
public interface PlayerStatisticsService {
    /**
     * Retrieves the statistics of a player for a specific season.
     *
     * @param playerId the unique identifier of the player whose season statistics are being retrieved
     * @param season   the identifier of the season for which the statistics are being fetched
     * @return a {@code Mono} emitting the {@code PlayerSeasonStats} containing the player's season statistics
     * or empty if no statistics are found for the specified player and season
     */
    Mono<PlayerSeasonStats> getPlayerSeasonStatistics(String playerId, String season);

    /**
     * Persists the provided player game statistics to the database and updates the corresponding
     * player season statistics. If no season statistics exist, a new record is created.
     *
     * @param playerGameStats the player game statistics object to be saved
     * @return a Mono emitting the saved player game statistics
     */
    Mono<PlayerGameStats> savePlayerStatistics(PlayerGameStats playerGameStats);

    /**
     * Retrieves all player season statistics.
     *
     * @return a {@link Flux} emitting all {@link PlayerSeasonStats} objects, or an empty {@link Flux} if none exist.
     */
    Flux<PlayerSeasonStats> getAllPlayerSeasonStatistics();

}
