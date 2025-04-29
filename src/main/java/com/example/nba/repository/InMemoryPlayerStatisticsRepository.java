package com.example.nba.repository;

import com.example.nba.model.PlayerGameStats;
import com.example.nba.repository.key.KeyGenerator;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory repository for managing player game statistics.
 * <p>
 * This class is a thread-safe implementation of {@link PlayerStatisticsRepository}
 * that uses a {@link ConcurrentHashMap} as the underlying storage mechanism.
 * It provides methods to store and retrieve {@link PlayerGameStats} objects
 * based on the player's unique identifier.
 * <p>
 * Key Features:
 * - Uses in-memory storage for quick access to player game statistics.
 * - Supports concurrent access and modification through thread-safe operations.
 * <p>
 * Limitations:
 * - Data is non-persistent and will be lost when the application shuts down.
 * - Designed for single-node applications; does not support distributed storage.
 */
@Repository
public class InMemoryPlayerStatisticsRepository implements PlayerStatisticsRepository {
    private final ConcurrentHashMap<String, PlayerGameStats> storage = new ConcurrentHashMap<>();
    private final KeyGenerator keyGenerator;

    public InMemoryPlayerStatisticsRepository(KeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }

    @Override
    public Mono<PlayerGameStats> findByPlayerId(String playerId) {
        return Mono.justOrEmpty(storage.get(keyGenerator.generateKey(playerId)));
    }

    @Override
    public Mono<PlayerGameStats> save(PlayerGameStats playerGameStats) {
        return Mono.justOrEmpty(playerGameStats)
                .map(stats -> {
                    String key = keyGenerator.generateKey(stats.playerId(), stats.gameId());
                    storage.put(key, stats);
                    return stats;
                });
    }


}