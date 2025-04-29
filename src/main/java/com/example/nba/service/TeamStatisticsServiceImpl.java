package com.example.nba.service;

import com.example.nba.model.TeamSeasonStats;
import com.example.nba.repository.TeamStatisticsStatisticsRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of the {@link TeamStatisticsService} interface for managing team statistics.
 * This service acts as a mediator between the underlying repository and business logic,
 * handling operations to save and retrieve aggregated team statistics for specific seasons.
 * <p>
 * Methods of this service delegate calls to the {@link TeamStatisticsStatisticsRepository}.
 */
@Service
public class TeamStatisticsServiceImpl implements TeamStatisticsService {

    private final TeamStatisticsStatisticsRepository repository;

    public TeamStatisticsServiceImpl(TeamStatisticsStatisticsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<TeamSeasonStats> save(TeamSeasonStats teamSeasonStats) {
        return repository.save(teamSeasonStats);
    }

    @Override
    public Mono<TeamSeasonStats> getTeamStats(String teamId, String season) {
        return repository.findByTeamId(teamId, season);
    }

    @Override
    public Flux<TeamSeasonStats> getAllTeamStats() {
        return repository.findAll();
    }

}