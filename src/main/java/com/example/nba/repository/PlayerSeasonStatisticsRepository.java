package com.example.nba.repository;

import com.example.nba.model.PlayerSeasonStats;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository interface for managing player season statistics data.
 * <p>
 * This interface defines methods for saving and retrieving player season statistics
 * based on the player's unique identifier and the associated season. It serves as the
 * contract for accessing and persisting {@link PlayerSeasonStats} objects in a storage system.
 * <p>
 * Implementations of this interface should ensure thread-safety and consistency
 * when interacting with the underlying data storage.
 */
public interface PlayerSeasonStatisticsRepository {
    /**
     * Persists or updates player season statistics in the storage system.
     * <p>
     * This method saves the provided {@link PlayerSeasonStats} object to the underlying data storage.
     * If the player's statistics for the given season already exist, they will be updated.
     *
     * @param playerSeasonStats the player season statistics to save; must not be null
     * @return a {@link Mono} emitting the saved {@link PlayerSeasonStats}, or
     * an error if the saving process fails
     */
    Mono<PlayerSeasonStats> save(PlayerSeasonStats playerSeasonStats);

    /**
     * Retrieves the seasonal statistics of a player based on the player's unique identifier
     * and the specified season.
     *
     * @param playerId the unique identifier of the player whose statistics are being retrieved
     * @param season   the season identifier for which the player's statistics are being retrieved
     * @return a {@code Mono} containing the {@code PlayerSeasonStats} for the specified player
     * and season, or an empty {@code Mono} if no matching statistics are found
     */
    Mono<PlayerSeasonStats> findByPlayerIdAndSeason(String playerId, String season);

    /**
     * Retrieves all player season statistics.
     *
     * @return a {@link Flux} emitting all {@link PlayerSeasonStats} objects, or an empty {@link Flux} if none exist.
     */
    Flux<PlayerSeasonStats> findAll();

}

