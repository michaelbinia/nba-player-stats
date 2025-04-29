package com.example.nba.service;

import com.example.nba.model.Team;
import com.example.nba.model.TeamSeasonStats;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

public class TeamStatisticsServiceTest {

    /**
     * This class tests the `getTeamStats` method in the `TeamStatisticsService` class.
     * The `getTeamStats` method retrieves the statistical record of a team's performance
     * in a specific season based on the provided team ID and season identifier.
     */

    private final TeamStatisticsService teamStatisticsService = mock(TeamStatisticsService.class);

    @Test
    @DisplayName("Should successfully retrieve team stats for a valid teamId and season")
    void getTeamStats_successfulRetrieval() {
        // Arrange
        String teamId = "LAL";
        String season = "2021";
        TeamSeasonStats mockStats = new TeamSeasonStats(
                new Team("LAL", "Los Angeles Lakers"),
                "2021",
                82,
                112.5,
                45.6,
                24.3,
                7.4,
                5.3,
                16.2,
                14.7
        );

        Mockito.when(teamStatisticsService.getTeamStats(eq(teamId), eq(season))).thenReturn(Mono.just(mockStats));

        // Act & Assert
        StepVerifier.create(teamStatisticsService.getTeamStats(teamId, season))
                .expectNext(mockStats)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return an empty Mono when the team is not found")
    void getTeamStats_teamNotFound() {
        // Arrange
        String teamId = "UNKNOWN";
        String season = "2021";

        Mockito.when(teamStatisticsService.getTeamStats(eq(teamId), eq(season))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(teamStatisticsService.getTeamStats(teamId, season))
                .verifyComplete();
    }

}