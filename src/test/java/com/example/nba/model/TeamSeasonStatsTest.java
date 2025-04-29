package com.example.nba.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("TeamSeasonStats Tests")
class TeamSeasonStatsTest {

    @Test
    @DisplayName("Should correctly update averages and total games when adding a new game")
    void testWithNewGameUpdatesCorrectly() {
        Team team = new Team("team1", "Team One");
        TeamSeasonStats initialStats = new TeamSeasonStats(
                team,
                "2023",
                1,
                100,
                50,
                25,
                10,
                5,
                15,
                8
        );

        PlayerGameStats newGameStats = new PlayerGameStats(
                "gameStats1",
                "player1",
                "game1",
                "team1",
                LocalDateTime.now(),
                "2023",
                110,
                55,
                30,
                12,
                6,
                16,
                7,
                BigDecimal.valueOf(30.0)
        );

        TeamSeasonStats updatedStats = initialStats.withNewGame(newGameStats);

        assertEquals(2, updatedStats.totalGamesPlayed());
        assertEquals(105, updatedStats.averageTeamPoints());
        assertEquals(52.5, updatedStats.averageTeamRebounds());
        assertEquals(27.5, updatedStats.averageTeamAssists());
        assertEquals(11, updatedStats.averageTeamSteals());
        assertEquals(5.5, updatedStats.averageTeamBlocks());
        assertEquals(15.5, updatedStats.averageTeamFouls());
        assertEquals(7.5, updatedStats.averageTeamTurnovers());
    }

    @Test
    @DisplayName("Should throw exception when team ID does not match")
    void testWithNewGameThrowsOnMismatchedTeamId() {
        Team team = new Team("team1", "Team One");
        TeamSeasonStats initialStats = new TeamSeasonStats(
                team,
                "2023",
                1,
                100,
                50,
                25,
                10,
                5,
                15,
                8
        );

        PlayerGameStats newGameStats = new PlayerGameStats(
                "gameStats1",
                "player1",
                "game1",
                "team2",
                LocalDateTime.now(),
                "2023",
                110,
                55,
                30,
                12,
                6,
                16,
                7,
                BigDecimal.valueOf(30.0)
        );

        assertThrows(IllegalArgumentException.class, () -> initialStats.withNewGame(newGameStats),
                "Game stats team ID does not match season stats team ID");
    }

    @Test
    @DisplayName("Should throw exception when season does not match")
    void testWithNewGameThrowsOnMismatchedSeason() {
        Team team = new Team("team1", "Team One");
        TeamSeasonStats initialStats = new TeamSeasonStats(
                team,
                "2023",
                1,
                100,
                50,
                25,
                10,
                5,
                15,
                8
        );

        PlayerGameStats newGameStats = new PlayerGameStats(
                "gameStats1",
                "player1",
                "game1",
                "team1",
                LocalDateTime.now(),
                "2024",
                110,
                55,
                30,
                12,
                6,
                16,
                7,
                BigDecimal.valueOf(30.0)
        );

        assertThrows(IllegalArgumentException.class, () -> initialStats.withNewGame(newGameStats),
                "Game stats season does not match season stats season");
    }
}