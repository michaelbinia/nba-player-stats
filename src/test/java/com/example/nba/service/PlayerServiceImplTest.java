package com.example.nba.service;

import com.example.nba.data.PlayerInitializer;
import com.example.nba.model.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class PlayerServiceImplTest {

    @MockBean
    private PlayerInitializer playerInitializer;

    @Autowired
    private PlayerServiceImpl playerService;

    @Test
    void testGetPlayerById_PlayerExists_ReturnsPlayer() {
        Player expectedPlayer = new Player("1", "LeBron James");
        when(playerInitializer.getPlayer(eq("1"))).thenReturn(expectedPlayer);

        Optional<Player> result = playerService.getPlayerById("1");

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(expectedPlayer);
    }

    @Test
    void testGetPlayerById_PlayerDoesNotExist_ReturnsEmptyOptional() {
        when(playerInitializer.getPlayer(eq("999"))).thenReturn(null);

        Optional<Player> result = playerService.getPlayerById("999");

        assertThat(result).isNotPresent();
    }

    @Test
    void testGetPlayerById_NullId_ThrowsException() {
        String nullId = null;

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class,
                () -> playerService.getPlayerById(nullId));
    }
}