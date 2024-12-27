package com.example.countdown_game;

import com.example.countdown_game.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CountdownGameApplicationTests {

    @Autowired
    private GameService gameService;

    @Test
    void contextLoads() {
        assertNotNull(gameService, "GameService should be loaded in the application context");
    }

    @Test
    void testGenerateVowels() {
        int count = 3;
        List<Character> vowels = gameService.generateVowels(count);
        assertNotNull(vowels, "Vowels list should not be null");
        assertEquals(count, vowels.size(), "The size of the vowels list should match the requested count");

        for (Character ch : vowels) {
            assertTrue("AEIOU".contains(ch.toString()), "Generated letter should be a vowel");
        }
    }

    @Test
    void testGenerateConsonants() {
        int count = 5;
        List<Character> consonants = gameService.generateConsonants(count);
        assertNotNull(consonants, "Consonants list should not be null");
        assertEquals(count, consonants.size(), "The size of the consonants list should match the requested count");

        for (Character ch : consonants) {
            assertFalse("AEIOU".contains(ch.toString()), "Generated letter should be a consonant");
        }
    }

    @Test
    void testValidateWordWithValidInput() {
        String word = "CAT";
        String currentLetters = "CATA";
        boolean isValid = gameService.validateWord(word, currentLetters);
        assertTrue(isValid, "The word should be valid if it can be formed using current letters");
    }

    @Test
    void testValidateWordWithInvalidInput() {
        String word = "DOG";
        String currentLetters = "CATA";
        boolean isValid = gameService.validateWord(word, currentLetters);
        assertFalse(isValid, "The word should be invalid if it cannot be formed using current letters");
    }
}
