package com.example.nba.repository;

import com.example.nba.model.Team;
import com.example.nba.model.TeamSeasonStats;
import com.example.nba.repository.key.KeyGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@SpringBootTest
class InMemoryTeamStatisticsRepositoryTest {

    @Test
    @DisplayName("Should save valid TeamSeasonStats and return the saved instance")
    void testSaveValidTeamSeasonStats() {
        KeyGenerator mockKeyGenerator = Mockito.mock(KeyGenerator.class);
        InMemoryTeamStatisticsRepository repository = new InMemoryTeamStatisticsRepository(mockKeyGenerator);

        Team team = new Team("team1", "Lakers");
        TeamSeasonStats stats = new TeamSeasonStats(
                team,
                "2023",
                82,
                110.5,
                45.8,
                25.6,
                8.1,
                5.2,
                18.3,
                14.7
        );
        when(mockKeyGenerator.generateKey(stats.team().id(), stats.season()))
                .thenReturn("team1_2023");

        StepVerifier.create(repository.save(stats))
                .expectNext(stats)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return empty Mono when saving null TeamSeasonStats")
    void testSaveNullTeamSeasonStats() {
        KeyGenerator mockKeyGenerator = Mockito.mock(KeyGenerator.class);
        InMemoryTeamStatisticsRepository repository = new InMemoryTeamStatisticsRepository(mockKeyGenerator);

        StepVerifier.create(repository.save(null))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should overwrite existing TeamSeasonStats for the same team and season")
    void testSaveOverwritesExistingStats() {
        KeyGenerator mockKeyGenerator = Mockito.mock(KeyGenerator.class);
        InMemoryTeamStatisticsRepository repository = new InMemoryTeamStatisticsRepository(mockKeyGenerator);

        Team team = new Team("team1", "Lakers");
        TeamSeasonStats initialStats = new TeamSeasonStats(
                team,
                "2023",
                82,
                110.5,
                45.8,
                25.6,
                8.1,
                5.2,
                18.3,
                14.7
        );
        TeamSeasonStats updatedStats = new TeamSeasonStats(
                team,
                "2023",
                82,
                112.0,
                47.0,
                26.2,
                9.0,
                6.0,
                19.0,
                15.0
        );

        when(mockKeyGenerator.generateKey(team.id(), "2023"))
                .thenReturn("team1_2023");

        repository.save(initialStats).block();
        StepVerifier.create(repository.save(updatedStats))
                .expectNext(updatedStats)
                .verifyComplete();

        StepVerifier.create(repository.findByTeamId("team1", "2023"))
                .expectNext(updatedStats)
                .verifyComplete();
    }
}