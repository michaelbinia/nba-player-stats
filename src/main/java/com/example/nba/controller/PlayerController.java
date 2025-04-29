package com.example.nba.controller;

import com.example.nba.model.Player;
import com.example.nba.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/players")
public class PlayerController {
    private static final Logger logger = LoggerFactory.getLogger(PlayerController.class);
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
        logger.info("PlayerController initialized");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Player>> listAllPlayers() {
        try {
            List<Player> players = new ArrayList<>(playerService.getAllPlayers());
            logger.info("Retrieved {} players successfully", players.size());
            return ResponseEntity.ok(players);
        } catch (Exception e) {
            logger.error("Error retrieving players: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}