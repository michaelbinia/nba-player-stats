package com.example.nba.data;

import com.example.nba.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Initializes and manages NBA team data.
 * This class implements {@link CommandLineRunner} to automatically
 * initialize a predefined set of teams when the application starts.
 * Provides access to team information through a map-based storage system.
 */
@Component
public class TeamInitializer implements CommandLineRunner {
    private final Map<String, Team> teams = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(PlayerInitializer.class);

    @Override
    public void run(String... args) {
        // Create sample teams
        createTeam("1", "Los Angeles Lakers");
        createTeam("2", "Golden State Warriors");
        createTeam("3", "Phoenix Suns");
        createTeam("4", "Milwaukee Bucks");
        createTeam("5", "Denver Nuggets");
        createTeam("6", "Philadelphia 76ers");
        createTeam("7", "Dallas Mavericks");
        createTeam("8", "Boston Celtics");
        createTeam("9", "Memphis Grizzlies");
        createTeam("10", "Brooklyn Nets");

        logger.info("Initialized {} players", teams.size());
    }

    private void createTeam(String id, String name) {
        Team team = new Team(id, name);
        teams.put(id, team);
    }

    public Map<String, Team> getTeams() {
        return teams;
    }

    public Team getTeam(String id) {
        return teams.get(id);
    }

}