package com.example.nba.service;

import com.example.nba.model.Team;

import java.util.List;

/**
 * Service interface for managing NBA teams.
 * <p>
 * This interface defines the contract for retrieving team-related data from the system.
 * Implementations of this interface are expected to provide data access mechanisms to
 * fetch information about teams.
 */
public interface TeamService {
    /**
     * Retrieves all teams in the system.
     *
     * @return list of all teams
     */
    List<Team> getAllTeams();
}