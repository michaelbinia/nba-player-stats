package com.example.nba.controller;

import com.example.nba.model.Team;
import com.example.nba.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * REST controller that provides endpoints to manage and retrieve information about basketball teams.
 */
@RestController
@RequestMapping("api/v1/teams")
public class TeamController {
    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * Retrieves all basketball teams in the system.
     *
     * @return list of all teams
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Team> getAllTeams() {
        logger.info("Fetching all teams.");
        try {
            List<Team> teams = teamService.getAllTeams();
            logger.info("Successfully fetched {} teams.", teams.size());
            return teams;
        } catch (Exception e) {
            logger.error("Error fetching teams.", e);
            // Handle the exception appropriately, e.g., rethrow or return an error response
            throw e;
        }
    }
}