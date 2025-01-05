package com.example.countdown_game.unitTest.controller;


import com.example.countdown_game.controller.ScoreController;
import com.example.countdown_game.entity.Score;
import com.example.countdown_game.service.ScoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ScoreControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ScoreService scoreService;

    private ScoreController scoreController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scoreController = new ScoreController(scoreService);
        mockMvc = MockMvcBuilders.standaloneSetup(scoreController).build();
    }

    @Test
    void testGetScoresByPlayer_ValidPlayer() throws Exception {
        String playerName = "Player1";
        List<Score> scores = Arrays.asList(
                new Score("Player1", 50, "ABCDE", "ACE"),
                new Score("Player1", 30, "FGHIJ", "FIG")
        );

        when(scoreService.getScoresForPlayer(playerName)).thenReturn(scores);

        mockMvc.perform(get("/api/scores/endScreen")
                        .param("playerName", playerName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].playerName").value("Player1"))
                .andExpect(jsonPath("$[0].scoreValue").value(50))
                .andExpect(jsonPath("$[1].scoreValue").value(30));

        verify(scoreService, times(1)).getScoresForPlayer(playerName);
    }

    @Test
    void testGetScoresByPlayer_PlayerNotFound() throws Exception {
        String playerName = "NonExistentPlayer";

        when(scoreService.getScoresForPlayer(playerName)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/scores/endScreen")
                        .param("playerName", playerName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(scoreService, times(1)).getScoresForPlayer(playerName);
    }

    @Test
    void testGetScoresByPlayer_ExceptionThrown() throws Exception {
        String playerName = "Player1";

        when(scoreService.getScoresForPlayer(playerName)).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/api/scores/endScreen")
                        .param("playerName", playerName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(scoreService, times(1)).getScoresForPlayer(playerName);
    }

    @Test
    void testResetAllGameData() throws Exception {
        doNothing().when(scoreService).resetAllGameData();

        mockMvc.perform(post("/api/scores/resetAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(scoreService, times(1)).resetAllGameData();
    }
}
