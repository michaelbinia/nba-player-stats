package com.example.nba.service;

import com.example.nba.model.Player;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing basketball players.
 */
public interface PlayerService {
    /**
     * Retrieves all players in the system.
     *
     * @return list of all players
     */
    List<Player> getAllPlayers();

    /**
     * Retrieves a player by their ID.
     *
     * @param id the player's unique identifier
     * @return Optional containing the player if found, empty otherwise
     */
    Optional<Player> getPlayerById(String id);
}
