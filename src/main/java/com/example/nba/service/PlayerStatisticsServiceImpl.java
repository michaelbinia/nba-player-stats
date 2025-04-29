package com.example.nba.service;

import com.example.nba.model.PlayerGameStats;
import com.example.nba.model.PlayerSeasonStats;
import com.example.nba.repository.PlayerSeasonStatisticsRepository;
import com.example.nba.repository.PlayerStatisticsRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service implementation for managing player statistics.
 * Handles both game-level and season-level statistics for players.
 */
@Service
public class PlayerStatisticsServiceImpl implements PlayerStatisticsService {
    private final PlayerStatisticsRepository gameStatsRepository;
    private final PlayerSeasonStatisticsRepository seasonStatsRepository;

    public PlayerStatisticsServiceImpl(
            PlayerStatisticsRepository gameStatsRepository,
            PlayerSeasonStatisticsRepository seasonStatsRepository) {
        this.gameStatsRepository = gameStatsRepository;
        this.seasonStatsRepository = seasonStatsRepository;
    }

    @Override
    public Mono<PlayerGameStats> savePlayerStatistics(PlayerGameStats playerGameStats) {
        return saveGameStats(playerGameStats)
                .flatMap(savedGameStats -> updateSeasonStats(savedGameStats)
                        .thenReturn(savedGameStats));
    }

    @Override
    public Mono<PlayerSeasonStats> getPlayerSeasonStatistics(String playerId, String season) {
        return seasonStatsRepository.findByPlayerIdAndSeason(playerId, season);
    }

    private Mono<PlayerGameStats> saveGameStats(PlayerGameStats gameStats) {
        return gameStatsRepository.save(gameStats);
    }

    private Mono<PlayerSeasonStats> updateSeasonStats(PlayerGameStats gameStats) {
        return findExistingSeasonStats(gameStats)
                .flatMap(existingStats -> updateExistingSeasonStats(existingStats, gameStats))
                .switchIfEmpty(createNewSeasonStats(gameStats));
    }

    private Mono<PlayerSeasonStats> findExistingSeasonStats(PlayerGameStats gameStats) {
        return seasonStatsRepository.findByPlayerIdAndSeason(gameStats.playerId(), gameStats.season());
    }

    private Mono<PlayerSeasonStats> updateExistingSeasonStats(PlayerSeasonStats existingStats,
                                                              PlayerGameStats gameStats) {
        return seasonStatsRepository.save(existingStats.withNewGame(gameStats));
    }

    private Mono<PlayerSeasonStats> createNewSeasonStats(PlayerGameStats gameStats) {
        return seasonStatsRepository.save(PlayerSeasonStats.fromFirstGame(gameStats));
    }

    @Override
    public Flux<PlayerSeasonStats> getAllPlayerSeasonStatistics() {
        return seasonStatsRepository.findAll();
    }

}