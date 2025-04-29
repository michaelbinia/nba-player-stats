package com.example.nba.controller;

import com.example.nba.service.PlayerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.*;

@WebFluxTest(PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PlayerService playerService;

    @Test
    @DisplayName("Test listAllPlayers - When an exception occurs")
    void testListAllPlayers_Exception() {
        when(playerService.getAllPlayers()).thenThrow(new RuntimeException("Something went wrong!"));

        webTestClient.get().uri("/api/v1/players")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();

        verify(playerService, times(1)).getAllPlayers();
    }
}