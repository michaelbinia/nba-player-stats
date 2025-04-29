package com.example.nba.service;

import com.example.nba.model.PlayerGameStats;
import com.example.nba.model.PlayerSeasonStats;
import com.example.nba.repository.PlayerSeasonStatisticsRepository;
import com.example.nba.repository.PlayerStatisticsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PlayerStatisticsServiceImpl class, specifically the savePlayerStatistics method.
 * <p>
 * The savePlayerStatistics method is responsible for:
 * - Saving a player's game statistics (PlayerGameStats) to the game stats repository.
 * - Updating or creating the player's season statistics (PlayerSeasonStats) based on the saved game statistics.
 * <p>
 * The tests validate different outcomes and scenarios of this method, ensuring correct behavior for
 * saving game stats, creating/updating season stats, and handling errors.
 */
class PlayerStatisticsServiceImplTest {

    @Mock
    private PlayerStatisticsRepository gameStatsRepository;

    @Mock
    private PlayerSeasonStatisticsRepository seasonStatsRepository;

    @InjectMocks
    private PlayerStatisticsServiceImpl playerStatisticsService;

    PlayerStatisticsServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Save player game statistics and create new season statistics when no existing season statistics are found")
    void testSavePlayerStatistics_NewSeasonStats() {
        PlayerGameStats playerGameStats = createValidPlayerGameStats();

        when(gameStatsRepository.save(any(PlayerGameStats.class))).thenReturn(Mono.just(playerGameStats));
        when(seasonStatsRepository.findByPlayerIdAndSeason(eq(playerGameStats.playerId()), eq(playerGameStats.season())))
                .thenReturn(Mono.empty());
        when(seasonStatsRepository.save(any(PlayerSeasonStats.class)))
                .thenReturn(Mono.just(PlayerSeasonStats.fromFirstGame(playerGameStats)));

        StepVerifier.create(playerStatisticsService.savePlayerStatistics(playerGameStats))
                .expectNext(playerGameStats)
                .verifyComplete();

        verify(gameStatsRepository, times(1)).save(playerGameStats);
        verify(seasonStatsRepository, times(1)).findByPlayerIdAndSeason(playerGameStats.playerId(), playerGameStats.season());
        verify(seasonStatsRepository, times(1)).save(any(PlayerSeasonStats.class));
    }

    @Test
    @DisplayName("Save player game statistics and update existing season statistics if they already exist")
    void testSavePlayerStatistics_UpdateExistingSeasonStats() {
        PlayerGameStats playerGameStats = createValidPlayerGameStats();
        PlayerSeasonStats existingSeasonStats = PlayerSeasonStats.fromFirstGame(playerGameStats);

        when(gameStatsRepository.save(any(PlayerGameStats.class))).thenReturn(Mono.just(playerGameStats));
        when(seasonStatsRepository.findByPlayerIdAndSeason(eq(playerGameStats.playerId()), eq(playerGameStats.season())))
                .thenReturn(Mono.just(existingSeasonStats));
        when(seasonStatsRepository.save(any(PlayerSeasonStats.class)))
                .thenReturn(Mono.just(existingSeasonStats.withNewGame(playerGameStats)));

        StepVerifier.create(playerStatisticsService.savePlayerStatistics(playerGameStats))
                .expectNext(playerGameStats)
                .verifyComplete();

        verify(gameStatsRepository, times(1)).save(playerGameStats);
        verify(seasonStatsRepository, times(1)).findByPlayerIdAndSeason(playerGameStats.playerId(), playerGameStats.season());
        verify(seasonStatsRepository, times(1)).save(existingSeasonStats.withNewGame(playerGameStats));
    }

    @Test
    @DisplayName("Handle failure when saving game statistics")
    void testSavePlayerStatistics_GameStatsSaveFails() {
        PlayerGameStats playerGameStats = createValidPlayerGameStats();

        when(gameStatsRepository.save(any(PlayerGameStats.class))).thenReturn(Mono.error(new RuntimeException("Save failed")));

        StepVerifier.create(playerStatisticsService.savePlayerStatistics(playerGameStats))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && throwable.getMessage().equals("Save failed"))
                .verify();

        verify(gameStatsRepository, times(1)).save(playerGameStats);
        verify(seasonStatsRepository, times(0)).findByPlayerIdAndSeason(anyString(), anyString());
        verify(seasonStatsRepository, times(0)).save(any(PlayerSeasonStats.class));
    }

    @Test
    @DisplayName("Handle failure when saving season statistics after successfully saving game statistics")
    void testSavePlayerStatistics_SeasonStatsSaveFails() {
        PlayerGameStats playerGameStats = createValidPlayerGameStats();

        when(gameStatsRepository.save(any(PlayerGameStats.class))).thenReturn(Mono.just(playerGameStats));
        when(seasonStatsRepository.findByPlayerIdAndSeason(eq(playerGameStats.playerId()), eq(playerGameStats.season())))
                .thenReturn(Mono.empty());
        when(seasonStatsRepository.save(any(PlayerSeasonStats.class)))
                .thenReturn(Mono.error(new RuntimeException("Save failed")));

        StepVerifier.create(playerStatisticsService.savePlayerStatistics(playerGameStats))
                .expectErrorMatches(throwable -> throwable instanceof RuntimeException && throwable.getMessage().equals("Save failed"))
                .verify();

        verify(gameStatsRepository, times(1)).save(playerGameStats);
        verify(seasonStatsRepository, times(1)).findByPlayerIdAndSeason(playerGameStats.playerId(), playerGameStats.season());
        verify(seasonStatsRepository, times(1)).save(any(PlayerSeasonStats.class));
    }

    private PlayerGameStats createValidPlayerGameStats() {
        return new PlayerGameStats(
                "stat123",
                "player123",
                "game123",
                "team123",
                LocalDateTime.now(),
                "2025-2026",
                30,
                10,
                5,
                2,
                1,
                3,
                4,
                new BigDecimal("32.5")
        );
    }
}