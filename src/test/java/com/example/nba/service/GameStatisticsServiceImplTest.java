package com.example.nba.service;

import com.example.nba.data.TeamInitializer;
import com.example.nba.model.PlayerGameStats;
import com.example.nba.model.Team;
import com.example.nba.model.TeamSeasonStats;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class GameStatisticsServiceImplTest {

    @Autowired
    private GameStatisticsServiceImpl gameStatisticsService;

    @MockBean
    private PlayerStatisticsService playerStatisticsService;

    @MockBean
    private TeamStatisticsService teamStatisticsService;

    @MockBean
    private TeamInitializer teamInitializer;

    @Test
    @DisplayName("Saves player and team statistics successfully")
    void saveGameStatistics_shouldSavePlayerAndTeamStats() {
        PlayerGameStats playerGameStats = new PlayerGameStats(
                "stat123",
                "player123",
                "game123",
                "team123",
                LocalDateTime.now(),
                "2023",
                25,
                10,
                5,
                3,
                2,
                4,
                2,
                BigDecimal.valueOf(35.0)
        );

        Team team = new Team("team123", "Test Team");
        TeamSeasonStats teamStats = TeamSeasonStats.fromFirstGame(playerGameStats, team);

        Mockito.when(playerStatisticsService.savePlayerStatistics(any(PlayerGameStats.class)))
                .thenReturn(Mono.just(playerGameStats));
        Mockito.when(teamInitializer.getTeam("team123"))
                .thenReturn(team);
        Mockito.when(teamStatisticsService.getTeamStats("team123", "2023"))
                .thenReturn(Mono.just(teamStats));
        Mockito.when(teamStatisticsService.save(any(TeamSeasonStats.class)))
                .thenReturn(Mono.just(teamStats));

        StepVerifier.create(gameStatisticsService.saveGameStatistics(playerGameStats))
                .expectNextMatches(savedStats -> savedStats.equals(playerGameStats))
                .verifyComplete();
    }

    @Test
    @DisplayName("Handles missing team scenario correctly")
    void saveGameStatistics_shouldHandleMissingTeam() {
        PlayerGameStats playerGameStats = new PlayerGameStats(
                "stat123",
                "player123",
                "game123",
                "team999",
                LocalDateTime.now(),
                "2023",
                20,
                8,
                7,
                1,
                1,
                3,
                1,
                BigDecimal.valueOf(30.0)
        );

        Mockito.when(playerStatisticsService.savePlayerStatistics(any(PlayerGameStats.class)))
                .thenReturn(Mono.just(playerGameStats));
        Mockito.when(teamInitializer.getTeam("team999"))
                .thenReturn(null);

        StepVerifier.create(gameStatisticsService.saveGameStatistics(playerGameStats))
                .expectErrorMatches(throwable ->
                        throwable instanceof IllegalStateException &&
                                throwable.getMessage().contains("Team not found for ID: team999"))
                .verify();
    }

    @Test
    @DisplayName("Creates new team statistics if they don't exist")
    void saveGameStatistics_shouldCreateNewTeamStatsIfNotExist() {
        PlayerGameStats playerGameStats = new PlayerGameStats(
                "stat456",
                "player456",
                "game456",
                "team456",
                LocalDateTime.now(),
                "2023",
                30,
                12,
                8,
                4,
                3,
                5,
                2,
                BigDecimal.valueOf(40.0)
        );

        Team team = new Team("team456", "Another Team");
        TeamSeasonStats newTeamStats = TeamSeasonStats.fromFirstGame(playerGameStats, team);

        Mockito.when(playerStatisticsService.savePlayerStatistics(any(PlayerGameStats.class)))
                .thenReturn(Mono.just(playerGameStats));
        Mockito.when(teamInitializer.getTeam("team456"))
                .thenReturn(team);
        Mockito.when(teamStatisticsService.getTeamStats("team456", "2023"))
                .thenReturn(Mono.empty());
        Mockito.when(teamStatisticsService.save(any(TeamSeasonStats.class)))
                .thenReturn(Mono.just(newTeamStats));

        StepVerifier.create(gameStatisticsService.saveGameStatistics(playerGameStats))
                .expectNextMatches(savedStats -> savedStats.equals(playerGameStats))
                .verifyComplete();
    }

    @Test
    @DisplayName("Handles player statistics save error gracefully")
    void saveGameStatistics_shouldHandlePlayerStatisticsSaveError() {
        PlayerGameStats playerGameStats = new PlayerGameStats(
                "stat789",
                "player789",
                "game789",
                "team789",
                LocalDateTime.now(),
                "2023",
                15,
                5,
                2,
                0,
                0,
                2,
                1,
                BigDecimal.valueOf(20.0)
        );

        Mockito.when(playerStatisticsService.savePlayerStatistics(any(PlayerGameStats.class)))
                .thenReturn(Mono.error(new RuntimeException("Failed to save player statistics")));

        StepVerifier.create(gameStatisticsService.saveGameStatistics(playerGameStats))
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().equals("Failed to save player statistics"))
                .verify();
    }
}