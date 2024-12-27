package com.example.countdown_game.service;

import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Service class that handles the core game logic for the Countdown Game.
 *
 * <p>This service provides functionality to generate vowels and consonants,
 * as well as validate user-submitted words based on the current game letters.</p>
 */
@Service
public class GameService {
    Random random = new Random();
    private static final List<Character> VOWELS = Arrays.asList('A', 'E', 'I', 'O', 'U');
    private static final List<Character> CONSONANTS = Arrays.asList(
            'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'
    );

    /**
     * Generates a specified number of random vowels.
     *
     * @param count The number of vowels to generate
     * @return A list of randomly selected vowels
     */
    public List<Character> generateVowels(int count) {
        List<Character> vowels = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            vowels.add(VOWELS.get(random.nextInt(VOWELS.size())));
        }
        return vowels;
    }

    /**
     * Generates a specified number of random consonants.
     *
     * @param count The number of consonants to generate
     * @return A list of randomly selected consonants
     */
    public List<Character> generateConsonants(int count) {

        List<Character> consonants = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            consonants.add(CONSONANTS.get(random.nextInt(CONSONANTS.size())));
        }
        return consonants;
    }

    /**
     * Validates a given word against the current set of available letters.
     *
     * <p>This method checks if the given word can be formed using the
     * provided letters. It ensures that each character in the word
     * appears within the available letters and that no character is
     * used more times than it appears in the letter set.</p>
     *
     * @param word    The word to validate
     * @param letters A string of letters available in the game session
     * @return {@code true} if the word is valid; {@code false} otherwise
     */
    public boolean validateWord(String word, String letters) {
        Map<Character, Integer> letterCount = new HashMap<>();

        // Count occurrences of each letter in the provided letter set
        for (char letter : letters.toCharArray()) {
            letterCount.put(letter, letterCount.getOrDefault(letter, 0) + 1);
        }

        // Validate the word against the available letters
        for (char ch : word.toUpperCase().toCharArray()) {
            if (!letterCount.containsKey(ch) || letterCount.get(ch) == 0) {
                return false;
            }
            letterCount.put(ch, letterCount.get(ch) - 1);
        }
        return true;
    }
}
