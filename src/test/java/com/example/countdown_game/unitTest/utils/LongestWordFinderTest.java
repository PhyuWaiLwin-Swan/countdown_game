package com.example.countdown_game.unitTest.utils;



import com.example.countdown_game.utils.LongestWordFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LongestWordFinderTest {

    @BeforeEach
    void setUp() throws IOException {
        // Initialize the dictionary before running tests
        LongestWordFinder.loadDictionary();
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

    @Test
    void testGeneratePermutations_FullLength() {
        // Arrange
        char[] letters = {'A', 'B', 'C'};

        // Act
        List<String> permutations = LongestWordFinder.generatePermutations(letters, letters.length);

        // Assert
        assertNotNull(permutations, "Permutations list should not be null");
        assertEquals(6, permutations.size(), "Expected 6 permutations for 3 characters");
        assertTrue(permutations.contains("ABC"), "Permutations should contain 'ABC'");
        assertTrue(permutations.contains("ACB"), "Permutations should contain 'ACB'");
        assertTrue(permutations.contains("BAC"), "Permutations should contain 'BAC'");
        assertTrue(permutations.contains("BCA"), "Permutations should contain 'BCA'");
        assertTrue(permutations.contains("CAB"), "Permutations should contain 'CAB'");
        assertTrue(permutations.contains("CBA"), "Permutations should contain 'CBA'");
    }

    @Test
    void testGeneratePermutations_PartialLength() {
        // Arrange
        char[] letters = {'A', 'B', 'C'};

        // Act
        List<String> permutations = LongestWordFinder.generatePermutations(letters, 2);

        // Assert
        assertNotNull(permutations, "Permutations list should not be null");
        assertEquals(6, permutations.size(), "Expected 6 permutations for 2 characters from 3");
        assertTrue(permutations.contains("AB"), "Permutations should contain 'AB'");
        assertTrue(permutations.contains("AC"), "Permutations should contain 'AC'");
        assertTrue(permutations.contains("BA"), "Permutations should contain 'BA'");
        assertTrue(permutations.contains("BC"), "Permutations should contain 'BC'");
        assertTrue(permutations.contains("CA"), "Permutations should contain 'CA'");
        assertTrue(permutations.contains("CB"), "Permutations should contain 'CB'");
    }

    @Test
    void testGeneratePermutations_SingleCharacter() {
        // Arrange
        char[] letters = {'A'};

        // Act
        List<String> permutations = LongestWordFinder.generatePermutations(letters, 1);

        // Assert
        assertNotNull(permutations, "Permutations list should not be null");
        assertEquals(1, permutations.size(), "Expected 1 permutation for a single character");
        assertTrue(permutations.contains("A"), "Permutations should contain 'A'");
    }

    @Test
    void testGeneratePermutations_EmptyArray() {
        // Arrange
        char[] letters = {};

        // Act
        List<String> permutations = LongestWordFinder.generatePermutations(letters, 0);

        // Assert
        assertNotNull(permutations, "Permutations list should not be null");
        assertEquals(1, permutations.size(), "Expected 0 permutations for an empty array");
        assertTrue(permutations.contains(""), "Permutations should contain ''");
    }
}
