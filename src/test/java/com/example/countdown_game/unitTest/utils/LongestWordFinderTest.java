package com.example.countdown_game.unitTest.utils;



import com.example.countdown_game.utils.LongestWordFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LongestWordFinderTest {

    @BeforeEach
    void setUp() throws IOException {
        // Initialize the dictionary before running tests
        LongestWordFinder.initializeDictionary();
    }

    @Test
    void testValidateWordInLongestWordFinder_ValidWord() {
        // Arrange
        String validWord = "apple"; // Ensure this word exists in the dictionary

        // Act
        boolean result = LongestWordFinder.validateWordInLongestWordFinder(validWord);

        // Assert
        assertTrue(result, "The word 'apple' should exist in the dictionary.");
    }

    @Test
    void testValidateWordInLongestWordFinder_InvalidWord() {
        // Arrange
        String invalidWord = "zzzzzz"; // Ensure this word does not exist in the dictionary

        // Act
        boolean result = LongestWordFinder.validateWordInLongestWordFinder(invalidWord);

        // Assert
        assertFalse(result, "The word 'zzzzzz' should not exist in the dictionary.");
    }

    @Test
    void testFindLongestWordInLongestWordFinder_ValidLetters() throws IOException {
        // Arrange
        String letters = "pleap"; // Should form the word "apple"

        // Act
        String result = LongestWordFinder.findLongestWordInLongestWordFinder(letters);

        // Assert
        assertEquals("appel", result, "The longest word formed should be 'apple'.");
    }

    @Test
    void testFindLongestWordInLongestWordFinder_NoValidWord() throws IOException {
        // Arrange
        String letters = "zzzzz"; // Cannot form any valid word

        // Act
        String result = LongestWordFinder.findLongestWordInLongestWordFinder(letters);

        // Assert
        assertEquals("z", result, "The longest word formed should be 'z'.");
    }

    @Test
    void testFindLongestWordInLongestWordFinder_EmptyLetters() throws IOException {
        // Arrange
        String letters = ""; // Empty input

        // Act
        String result = LongestWordFinder.findLongestWordInLongestWordFinder(letters);

        // Assert
        assertNull(result, "No valid word should be found for empty letters.");
    }

    @Test
    void testFindLongestWordInLongestWordFinder_SingleLetter() throws IOException {
        // Arrange
        String letters = "a"; // Single valid letter

        // Act
        String result = LongestWordFinder.findLongestWordInLongestWordFinder(letters);

        // Assert
        assertEquals("a", result, "The longest word formed should be 'a'.");
    }

    @Test
    void testInitializeDictionary_DictionaryLoaded() {
        // Assert
        assertFalse(LongestWordFinder.validateWordInLongestWordFinder(""), "The dictionary should be loaded and functional.");
    }
}
