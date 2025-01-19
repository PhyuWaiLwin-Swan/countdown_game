package com.example.countdown_game.unitTest.service;

import com.example.countdown_game.entity.Score;
import com.example.countdown_game.repository.ScoreRepository;
import com.example.countdown_game.service.ScoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ScoreServiceTest {

    @Mock
    private ScoreRepository scoreRepository; // Mocking the repository

    @InjectMocks
    private ScoreService scoreService; // The service being tested

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes the mocks
    }

    @Test
    void testGetScoresForPlayer_Success() {
        // Arrange
        String playerName = "player1";
        Score score = new Score();
        score.setPlayerName(playerName);
        score.setAnswered("word");
        score.setSelectedAlphabet("ABCDE");
        score.setScoreValue(10);
        score.setLongestPossibleWord("bead");

        when(scoreRepository.findByPlayerName(playerName)).thenReturn(Collections.singletonList(score));

        // Act
        List<Score> result = scoreService.getScoresForPlayer(playerName);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("word", result.get(0).getAnswered());
        assertEquals("bead", result.get(0).getLongestPossibleWord());
        verify(scoreRepository, times(1)).findByPlayerName(playerName); // Verifying repository method call
    }

    @Test
    void testGetScoresForPlayer_Failure() {
        // Arrange
        String playerName = "player1";
        when(scoreRepository.findByPlayerName(playerName)).thenThrow(new RuntimeException("Database error"));

        // Act
        List<Score> result = scoreService.getScoresForPlayer(playerName);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty()); // It should return an empty list in case of error
        verify(scoreRepository, times(1)).findByPlayerName(playerName); // Verifying repository method call
    }

    @Test
    void testSaveScore_Success() {
        // Arrange
        String playerName = "player1";
        String currentLetters = "ABCDE";
        String word = "word";
        int scoreValue = 10;
        String longestPossibleWord = "bead";

        when(scoreRepository.save(any(Score.class))).thenReturn(null); // Mock save method

        // Act
        boolean result = scoreService.saveScore(playerName, currentLetters, word, scoreValue, longestPossibleWord);

        // Assert
        assertTrue(result); // The save operation should succeed
        verify(scoreRepository, times(1)).save(any(Score.class)); // Verify that save is called
    }

    @Test
    void testSaveScore_Failure() {
        // Arrange
        String playerName = "player1";
        String currentLetters = "ABCDE";
        String word = "word";
        int scoreValue = 10;
        String longestPossibleWord = "bead";

        when(scoreRepository.save(any(Score.class))).thenThrow(new RuntimeException("Database error"));

        // Act
        boolean result = scoreService.saveScore(playerName, currentLetters, word, scoreValue, longestPossibleWord);

        // Assert
        assertFalse(result); // The save operation should fail
        verify(scoreRepository, times(1)).save(any(Score.class)); // Verify that save is called
    }

    @Test
    void testResetAllGameData() {
        // Arrange
        doNothing().when(scoreRepository).deleteAllScores(); // Mocking the deleteAll method

        // Act
        scoreService.resetAllGameData();

        // Assert
        verify(scoreRepository, times(1)).deleteAllScores(); // Ensure that deleteAll was called exactly once
    }
}
