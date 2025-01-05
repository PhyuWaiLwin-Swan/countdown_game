package com.example.countdown_game.unitTest.controller;

import com.example.countdown_game.controller.GameController;
import com.example.countdown_game.service.GameService;
import com.example.countdown_game.service.ScoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GameService gameService;

    @Mock
    private ScoreService scoreService;

    @InjectMocks
    private GameController gameController;

    @Test
    public void testGetVowels() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

        when(gameService.generateVowels(3)).thenReturn(List.of('A', 'E', 'I'));

        mockMvc.perform(get("/api/game/vowels?count=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("A"))
                .andExpect(jsonPath("$[1]").value("E"))
                .andExpect(jsonPath("$[2]").value("I"));
    }

    @Test
    public void testGetConsonants() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

        when(gameService.generateConsonants(3)).thenReturn(List.of('B', 'C', 'D'));

        mockMvc.perform(get("/api/game/consonants?count=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("B"))
                .andExpect(jsonPath("$[1]").value("C"))
                .andExpect(jsonPath("$[2]").value("D"));
    }

    @Test
    public void testValidateWord() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

        String currentLetters = "ABCDEF";
        String word = "ACE";
        String playerName = "Player1";

        when(gameService.validateWord(word, currentLetters)).thenReturn(true);

        mockMvc.perform(post("/api/game/validate")
                        .param("word", word)
                        .param("currentLetters", currentLetters)
                        .param("playerName", playerName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value(word))
                .andExpect(jsonPath("$.isValid").value(true))
                .andExpect(jsonPath("$.score").value(word.length()));
    }
    @Test
    public void testValidateWord_InvalidWord() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

        String currentLetters = "ABCDEF";
        String word = "XYZ";
        String playerName = "Player2";

        when(gameService.validateWord(word, currentLetters)).thenReturn(false);

        mockMvc.perform(post("/api/game/validate")
                        .param("word", word)
                        .param("currentLetters", currentLetters)
                        .param("playerName", playerName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value(word))
                .andExpect(jsonPath("$.isValid").value(false))
                .andExpect(jsonPath("$.score").value(0));
    }

    @Test
    public void testValidateWord_EmptyWordInput() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

        String currentLetters = "ABCDEF";
        String playerName = "Player3";

        // Assume an empty word is valid and scores 0
        when(gameService.validateWord("", currentLetters)).thenReturn(false);


        mockMvc.perform(post("/api/game/validate")
                        .param("word", "")
                        .param("currentLetters", currentLetters)
                        .param("playerName", playerName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value(""))
                .andExpect(jsonPath("$.isValid").value(false))
                .andExpect(jsonPath("$.score").value(0));
    }
    @Test
    public void testGetVowels_MissingParameter() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

        mockMvc.perform(get("/api/game/vowels")) // Missing 'count' parameter
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0)); // Expect no item by default
    }

    @Test
    public void testValidateWord_LargeLetterSet() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();

        String currentLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String word = "QUICK";
        String playerName = "Player5";

        when(gameService.validateWord(word, currentLetters)).thenReturn(true);

        mockMvc.perform(post("/api/game/validate")
                        .param("word", word)
                        .param("currentLetters", currentLetters)
                        .param("playerName", playerName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.word").value(word))
                .andExpect(jsonPath("$.isValid").value(true));
    }


}
