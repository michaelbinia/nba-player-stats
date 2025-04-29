package com.example.nba.repository;

import com.example.nba.model.PlayerSeasonStats;
import com.example.nba.repository.key.KeyGenerator;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory repository implementation for managing player season statistics.
 * <p>
 * This class provides an in-memory storage solution for {@link PlayerSeasonStats}, which is
 * thread-safe and uses {@link ConcurrentHashMap} to store and retrieve player statistics
 * based on a generated key combining the player's ID and the season.
 * <p>
 * It implements {@link PlayerSeasonStatisticsRepository} to fulfill the contract of saving
 * player season statistics and retrieving them using a player ID and season identifier.
 * <p>
 * Key generation for storage is delegated to an injected {@link KeyGenerator}, making it
 * customizable for different key formats or algorithms.
 * <p>
 * Thread safety is ensured by utilizing the thread-safe features of {@link ConcurrentHashMap}.
 */
@Repository
public class InMemoryPlayerSeasonStatisticsRepository implements PlayerSeasonStatisticsRepository {
    private final ConcurrentHashMap<String, PlayerSeasonStats> storage = new ConcurrentHashMap<>();
    private final KeyGenerator keyGenerator;

    public InMemoryPlayerSeasonStatisticsRepository(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }


    @Override
    public Mono<PlayerSeasonStats> save(PlayerSeasonStats playerSeasonStats) {
        return Mono.justOrEmpty(playerSeasonStats)
                .map(stats -> {
                    String key = keyGenerator.generateKey(stats.playerId(), stats.season());
                    storage.put(key, stats);
                    return stats;
                });
    }

    @Override
    public Mono<PlayerSeasonStats> findByPlayerIdAndSeason(String playerId, String season) {
        return Mono.justOrEmpty(storage.get(keyGenerator.generateKey(playerId, season)));
    }

    @Override
    public Flux<PlayerSeasonStats> findAll() {
        return Flux.fromIterable(storage.values());
    }


}