package com.example.nba.repository;

import com.example.nba.model.PlayerGameStats;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Repository interface for managing player statistics data
 */
@Repository
public interface PlayerStatisticsRepository {
    /**
     * Finds player statistics by player ID
     *
     * @param playerId the unique identifier of the player
     * @return Mono containing the player statistics if found, empty Mono otherwise
     */
    Mono<PlayerGameStats> findByPlayerId(String playerId);

    /**
     * Saves or updates player statistics
     *
     * @param playerGameStats the player statistics to save
     * @return Mono containing the saved player statistics
     */
    Mono<PlayerGameStats> save(PlayerGameStats playerGameStats);



}