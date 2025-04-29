package com.example.nba.model;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents a team in the NBA system.
 * <p>
 * A Team is uniquely identified by an ID and has a name associated with it.
 * Both the team ID and team name are mandatory and cannot be blank.
 */
public record Team(
        @NotBlank(message = "Team ID is required")
        String id,

        @NotBlank(message = "Team name is required")
        String name
) {
}