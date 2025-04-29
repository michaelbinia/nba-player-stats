package com.example.nba.service;

import com.example.nba.model.TeamSeasonStats;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for managing team statistics, including creating and retrieving
 * aggregate statistics for a team's performance during a specific season.
 */
public interface TeamStatisticsService {
    /**
     * Persists the provided team season statistics into the underlying data repository.
     *
     * @param teamSeasonStats the team season statistics object to be saved, containing
     *                        details such as team ID, season, and various performance metrics
     * @return a {@code Mono} emitting the saved {@code TeamSeasonStats} object
     */
    Mono<TeamSeasonStats> save(TeamSeasonStats teamSeasonStats);

    /**
     * Retrieves the aggregate statistics for a team during a specific season.
     *
     * @param teamId the unique identifier of the team whose statistics are being retrieved
     * @param season the identifier of the season for which the team statistics are being fetched
     * @return a {@code Mono} emitting the {@code TeamSeasonStats} containing the team's season statistics,
     *         or an empty Mono if no statistics are available for the specified team and season
     */
    Mono<TeamSeasonStats> getTeamStats(String teamId, String season);

    /**
     * Retrieves all team statistics.
     *
     * @return a {@code Flux} emitting all {@code TeamSeasonStats} objects
     */
    Flux<TeamSeasonStats> getAllTeamStats();

}
