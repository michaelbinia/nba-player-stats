package com.example.nba.service;

import com.example.nba.data.TeamInitializer;
import com.example.nba.model.Team;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * Implementation of the TeamService interface that uses TeamInitializer
 * as the data source for NBA teams.
 */
@Service
public final class TeamServiceImpl implements TeamService {
    private final TeamInitializer teamInitializer;

    public TeamServiceImpl(@Nonnull TeamInitializer teamInitializer) {
        Assert.notNull(teamInitializer, "TeamInitializer must not be null");
        this.teamInitializer = teamInitializer;
    }

    @Override
    @Nonnull
    public List<Team> getAllTeams() {
        return convertTeamMapToList(teamInitializer.getTeams());
    }

    private List<Team> convertTeamMapToList(@Nonnull Map<String, Team> teamMap) {
        return teamMap.values()
                .stream()
                .toList();
    }
}