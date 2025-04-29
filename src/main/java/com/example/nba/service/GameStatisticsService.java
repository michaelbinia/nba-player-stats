package com.example.nba.service;

import com.example.nba.model.PlayerGameStats;
import com.example.nba.model.TeamSeasonStats;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface GameStatisticsService {

    Mono<PlayerGameStats> saveGameStatistics(PlayerGameStats playerGameStats);

    Mono<TeamSeasonStats> getTeamSeasonStats(String teamId, String season);

}