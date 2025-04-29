package com.example.nba.service;

import com.example.nba.data.PlayerInitializer;
import com.example.nba.model.Player;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the PlayerService interface that uses PlayerInitializer
 * as the data source.
 */
@Service
public final class PlayerServiceImpl implements PlayerService {
    private final PlayerInitializer playerInitializer;

    public PlayerServiceImpl(@Nonnull PlayerInitializer playerInitializer) {
        Assert.notNull(playerInitializer, "PlayerInitializer must not be null");
        this.playerInitializer = playerInitializer;
    }

    @Override
    @Nonnull
    public List<Player> getAllPlayers() {
        return convertPlayerMapToList(playerInitializer.getPlayers());
    }

    @Override
    @Nonnull
    public Optional<Player> getPlayerById(@Nonnull String id) {
        Assert.notNull(id, "Player ID must not be null");
        return Optional.ofNullable(playerInitializer.getPlayer(id));
    }

    private List<Player> convertPlayerMapToList(@Nonnull java.util.Map<String, Player> playerMap) {
        return playerMap.values()
                .stream()
                .toList();
    }
}