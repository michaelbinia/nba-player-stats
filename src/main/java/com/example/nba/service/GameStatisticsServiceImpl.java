package com.example.nba.service;

import com.example.nba.data.TeamInitializer;
import com.example.nba.model.PlayerGameStats;
import com.example.nba.model.Team;
import com.example.nba.model.TeamSeasonStats;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GameStatisticsServiceImpl implements GameStatisticsService {
    private final TeamInitializer teamInitializer;
    private final PlayerStatisticsService playerStatisticsService;
    private final TeamStatisticsService teamStatisticsService;

    public GameStatisticsServiceImpl(
            PlayerStatisticsService playerStatisticsService,
            TeamStatisticsService teamStatisticsService,
            TeamInitializer teamInitializer) {
        this.playerStatisticsService = playerStatisticsService;
        this.teamStatisticsService = teamStatisticsService;
        this.teamInitializer = teamInitializer;
    }

    @Override
    public Mono<PlayerGameStats> saveGameStatistics(PlayerGameStats playerGameStats) {
        return playerStatisticsService.savePlayerStatistics(playerGameStats)
                .flatMap(savedPlayerStats -> updateTeamStatistics(savedPlayerStats)
                        .thenReturn(savedPlayerStats));
    }

    @Override
    public Mono<TeamSeasonStats> getTeamSeasonStats(String teamId, String season) {
        return teamStatisticsService.getTeamStats(teamId, season);
    }

    private Mono<TeamSeasonStats> updateTeamStatistics(PlayerGameStats gameStats) {
        Team team = teamInitializer.getTeam(gameStats.teamId());
        if (team == null) {
            return Mono.error(new IllegalStateException("Team not found for ID: " + gameStats.teamId()));
        }

        return teamStatisticsService.getTeamStats(gameStats.teamId(), gameStats.season())
                .flatMap(existingTeamStats ->
                        teamStatisticsService.save(existingTeamStats.withNewGame(gameStats)))
                .switchIfEmpty(
                        teamStatisticsService.save(TeamSeasonStats.fromFirstGame(gameStats, team))
                );
    }

}