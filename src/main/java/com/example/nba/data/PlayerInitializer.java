package com.example.nba.data;

import com.example.nba.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Initializes and manages NBA player data.
 * Provides access to player information through a map-based storage.
 */
@Component
public class PlayerInitializer implements CommandLineRunner {
    private final Map<String, Player> players = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(PlayerInitializer.class);

    @Override
    public void run(String... args) {
        // Create sample players
        createPlayer("1", "LeBron James");
        createPlayer("2", "Stephen Curry");
        createPlayer("3", "Kevin Durant");
        createPlayer("4", "Giannis Antetokounmpo");
        createPlayer("5", "Nikola Jokic");
        createPlayer("6", "Joel Embiid");
        createPlayer("7", "Luka Doncic");
        createPlayer("8", "Jayson Tatum");
        createPlayer("9", "Ja Morant");
        createPlayer("10", "Devin Booker");
        createPlayer("11", "Michael Jordan");
        createPlayer("12", "Bugs Bunny");
        createPlayer("13", "Lola Bunny");
        createPlayer("14", "Bill Murray");

        logger.info("Initialized {} players", players.size());
    }

    private void createPlayer(String id, String name) {
        Player player = new Player(id, name);
        players.put(id, player);
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public Player getPlayer(String id) {
        return players.get(id);
    }

}