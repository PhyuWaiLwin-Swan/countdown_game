package com.example.countdown_game.unitTest.service;

import com.example.countdown_game.config.Config;
import com.example.countdown_game.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private Config config;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateVowels() {
        List<Character> vowels = gameService.generateVowels(3);
        assertEquals(3, vowels.size());
        assertTrue(vowels.stream().allMatch(vowel -> "AEIOU".contains(String.valueOf(vowel))));
    }

    @Test
    void testGenerateConsonants() {
        List<Character> consonants = gameService.generateConsonants(3);
        assertEquals(3, consonants.size());
        assertTrue(consonants.stream().allMatch(consonant ->
                "BCDFGHJKLMNPQRSTVWXYZ".contains(String.valueOf(consonant))));
    }

    @Test
    void testIsValidWord_Valid() {
        String validWord = "apple";
        when(restTemplate.getForObject(anyString(), eq(Object.class))).thenReturn(new Object());
        boolean result = gameService.isValidWord(validWord);
        assertTrue(result);
    }

    @Test
    void testIsValidWord_Invalid() {
        String invalidWord = "applz";
        when(restTemplate.getForObject(anyString(), eq(Object.class))).thenThrow(new RuntimeException("Word not found"));
        boolean result = gameService.isValidWord(invalidWord);
        assertFalse(result);
    }

    @Test
    void testValidateWord_ValidWord() {
        String word = "CAT";
        String letters = "CAT";
        boolean result = gameService.validateWord(word, letters);
        assertTrue(result);
    }

    @Test
    void testValidateWord_InvalidWord() {
        String word = "BAT";
        String letters = "CAT";
        boolean result = gameService.validateWord(word, letters);
        assertFalse(result);
    }

    @Test
    void testValidateWord_InsufficientLetters() {
        String word = "CATS";
        String letters = "CAT";
        boolean result = gameService.validateWord(word, letters);
        assertFalse(result);
    }
}