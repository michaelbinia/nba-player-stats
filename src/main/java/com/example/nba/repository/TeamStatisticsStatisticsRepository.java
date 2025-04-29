package com.example.nba.repository;

import com.example.nba.model.TeamSeasonStats;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository interface for managing NBA team season statistics data.
 * <p>
 * This interface defines methods for saving and retrieving team season statistics
 * based on the team's unique identifier and the associated season. It serves as the
 * contract for accessing and persisting {@code TeamSeasonStats} objects in a storage system.
 * <p>
 * Implementations of this interface should ensure thread-safety and consistency
 * when interacting with the underlying data storage.
 */
public interface TeamStatisticsStatisticsRepository {
    /**
     * Persists or updates the statistics of an NBA team for a specific season.
     * <p>
     * This method saves the provided {@code TeamSeasonStats} object to the underlying data storage.
     * If the statistics for the given team and season already exist, they will be updated.
     *
     * @param teamSeasonStats the aggregated statistics of the team for a specific season; must not be null
     * @return a {@code Mono} emitting the saved {@code TeamSeasonStats}, or an error if the saving process fails
     */
    Mono<TeamSeasonStats> save(TeamSeasonStats teamSeasonStats);

    /**
     * Retrieves the seasonal statistics for a specific NBA team based on the team's unique identifier
     * and the specified season.
     *
     * @param teamId the unique identifier of the team whose statistics are being retrieved
     * @param season the season identifier for which the team's statistics are being retrieved
     * @return a {@code Mono} containing the {@code TeamSeasonStats} for the specified team and season,
     * or an empty {@code Mono} if no matching statistics are found
     */
    Mono<TeamSeasonStats> findByTeamId(String teamId, String season);

    /**
     * Retrieves all team statistics.
     *
     * @return a {@code Flux} emitting all {@code TeamSeasonStats} objects.
     */
    Flux<TeamSeasonStats> findAll();

}
