package com.example.nba.controller;

import com.example.nba.model.PlayerGameStats;
import com.example.nba.model.PlayerSeasonStats;
import com.example.nba.model.TeamSeasonStats;
import com.example.nba.service.GameStatisticsService;
import com.example.nba.service.PlayerStatisticsService;
import com.example.nba.service.TeamStatisticsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/statistics")
public class StatisticsController {
    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    private final PlayerStatisticsService playerStatisticsService;
    private final GameStatisticsService gameStatisticsService;
    private final TeamStatisticsService teamStatisticsService;

    public StatisticsController(
            PlayerStatisticsService playerStatisticsService,
            GameStatisticsService gameStatisticsService,
            TeamStatisticsService teamStatisticsService
    ) {
        this.playerStatisticsService = playerStatisticsService;
        this.gameStatisticsService = gameStatisticsService;
        this.teamStatisticsService = teamStatisticsService;
    }

    @PostMapping("/player/stats")
    public Mono<ResponseEntity<PlayerGameStats>> createPlayerGameStats(
            @RequestBody @Valid PlayerGameStats gameStats) {
        logger.info("Recording game statistics for player: {}", gameStats.playerId());
        return gameStatisticsService.saveGameStatistics(gameStats)
                .map(ResponseEntity::ok)
                .doOnError(error -> logger.error("Failed to save game statistics: {}", error.getMessage()));

    }


    @GetMapping("/players/{playerId}/seasons/{season}")
    public Mono<ResponseEntity<PlayerSeasonStats>> getPlayerSeasonStats(
            @PathVariable String playerId,
            @PathVariable String season) {
        logger.info("Retrieving season statistics for player: {}, season: {}", playerId, season);
        return playerStatisticsService.getPlayerSeasonStatistics(playerId, season)
                .doOnError(error -> logger.error("Failed to retrieve player season stats: {}", error.getMessage()))
                .transform(this::wrapResponse);
    }

    @GetMapping("/teams/{teamId}/seasons/{season}")
    public Mono<ResponseEntity<TeamSeasonStats>> getTeamSeasonStats(
            @PathVariable String teamId,
            @PathVariable String season) {
        logger.info("Retrieving season statistics for team: {}, season: {}", teamId, season);
        return gameStatisticsService.getTeamSeasonStats(teamId, season)
                .doOnError(error -> logger.error("Failed to retrieve team season stats: {}", error.getMessage()))
                .transform(this::wrapResponse);
    }

    private <T> Mono<ResponseEntity<T>> wrapResponse(Mono<T> monoResult) {
        return monoResult
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/teams/stats")
    public Flux<TeamSeasonStats> getAllTeamStats() {
        return teamStatisticsService.getAllTeamStats();
    }

    @GetMapping("/players/season-stats")
    public Flux<PlayerSeasonStats> getAllPlayerSeasonStats() {
        return playerStatisticsService.getAllPlayerSeasonStatistics();
    }


}