package com.example.nba.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerSeasonStatsTest {

    @Test
    @DisplayName("Should correctly update stats with new game when playerId and season match")
    void testWithNewGame_Success() {
        PlayerSeasonStats initialStats = new PlayerSeasonStats(
                "player123",
                "2023-24",
                5,
                100,
                50,
                25,
                10,
                5,
                20,
                15,
                BigDecimal.valueOf(150),
                20.0,
                10.0,
                5.0,
                2.0,
                1.0,
                4.0,
                3.0,
                BigDecimal.valueOf(30)
        );

        PlayerGameStats newGameStats = new PlayerGameStats(
                "game123",
                "player123",
                "game321",
                "team999",
                LocalDateTime.now(),
                "2023-24",
                20,
                10,
                5,
                2,
                1,
                4,
                3,
                BigDecimal.valueOf(30.0)
        );

        PlayerSeasonStats updatedStats = initialStats.withNewGame(newGameStats);

        assertEquals(6, updatedStats.gamesPlayed());
        assertEquals(120, updatedStats.totalPoints());
        assertEquals(60, updatedStats.totalRebounds());
        assertEquals(30, updatedStats.totalAssists());
        assertEquals(12, updatedStats.totalSteals());
        assertEquals(6, updatedStats.totalBlocks());
        assertEquals(24, updatedStats.totalFouls());
        assertEquals(18, updatedStats.totalTurnovers());
        assertEquals(BigDecimal.valueOf(180.0), updatedStats.totalMinutesPlayed());
        assertEquals(20.0, updatedStats.avgPoints());
        assertEquals(10.0, updatedStats.avgRebounds());
        assertEquals(5.0, updatedStats.avgAssists());
        assertEquals(2.0, updatedStats.avgSteals());
        assertEquals(1.0, updatedStats.avgBlocks());
        assertEquals(4.0, updatedStats.avgFouls());
        assertEquals(3.0, updatedStats.avgTurnovers());
        assertEquals(BigDecimal.valueOf(30.0), updatedStats.avgMinutesPlayed());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when playerId does not match")
    void testWithNewGame_PlayerIdMismatch() {
        PlayerSeasonStats initialStats = new PlayerSeasonStats(
                "player123",
                "2023-24",
                5,
                100,
                50,
                25,
                10,
                5,
                20,
                15,
                BigDecimal.valueOf(150),
                20.0,
                10.0,
                5.0,
                2.0,
                1.0,
                4.0,
                3.0,
                BigDecimal.valueOf(30)
        );

        PlayerGameStats mismatchedPlayerGameStats = new PlayerGameStats(
                "game123",
                "differentPlayer",
                "game321",
                "team999",
                LocalDateTime.now(),
                "2023-24",
                20,
                10,
                5,
                2,
                1,
                4,
                3,
                BigDecimal.valueOf(30.0)
        );

        assertThrows(IllegalArgumentException.class, () -> initialStats.withNewGame(mismatchedPlayerGameStats));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when season does not match")
    void testWithNewGame_SeasonMismatch() {
        PlayerSeasonStats initialStats = new PlayerSeasonStats(
                "player123",
                "2023-24",
                5,
                100,
                50,
                25,
                10,
                5,
                20,
                15,
                BigDecimal.valueOf(150),
                20.0,
                10.0,
                5.0,
                2.0,
                1.0,
                4.0,
                3.0,
                BigDecimal.valueOf(30)
        );

        PlayerGameStats mismatchedSeasonGameStats = new PlayerGameStats(
                "game123",
                "player123",
                "game321",
                "team999",
                LocalDateTime.now(),
                "2022-23",
                20,
                10,
                5,
                2,
                1,
                4,
                3,
                BigDecimal.valueOf(30.0)
        );

        assertThrows(IllegalArgumentException.class, () -> initialStats.withNewGame(mismatchedSeasonGameStats));
    }

    @Test
    @DisplayName("Should correctly calculate averages with new game stats")
    void testWithNewGame_Averages() {
        PlayerSeasonStats initialStats = new PlayerSeasonStats(
                "player123",
                "2023-24",
                2,
                50,
                30,
                10,
                5,
                2,
                8,
                6,
                BigDecimal.valueOf(60),
                25.0,
                15.0,
                5.0,
                2.5,
                1.0,
                4.0,
                3.0,
                BigDecimal.valueOf(30)
        );

        PlayerGameStats newGameStats = new PlayerGameStats(
                "game456",
                "player123",
                "game654",
                "team999",
                LocalDateTime.now(),
                "2023-24",
                20,
                10,
                5,
                2,
                1,
                4,
                3,
                BigDecimal.valueOf(30.0)
        );

        PlayerSeasonStats updatedStats = initialStats.withNewGame(newGameStats);

        assertEquals(3, updatedStats.gamesPlayed());
        assertEquals(70, updatedStats.totalPoints());
        assertEquals(40, updatedStats.totalRebounds());
        assertEquals(15, updatedStats.totalAssists());
        assertEquals(7, updatedStats.totalSteals());
        assertEquals(3, updatedStats.totalBlocks());
        assertEquals(12, updatedStats.totalFouls());
        assertEquals(9, updatedStats.totalTurnovers());
        assertEquals(BigDecimal.valueOf(90.0), updatedStats.totalMinutesPlayed());
        assertEquals(23.33, updatedStats.avgPoints(), 0.01);
        assertEquals(13.33, updatedStats.avgRebounds(), 0.01);
        assertEquals(5.0, updatedStats.avgAssists());
        assertEquals(2.33, updatedStats.avgSteals(), 0.01);
        assertEquals(1.0, updatedStats.avgBlocks());
        assertEquals(4.0, updatedStats.avgFouls());
        assertEquals(3.0, updatedStats.avgTurnovers());
        assertEquals(BigDecimal.valueOf(30.0), updatedStats.avgMinutesPlayed());
    }
}