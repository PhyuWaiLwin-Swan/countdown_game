package com.example.countdown_game.service;



import com.example.countdown_game.utils.InputValidator;

import com.example.countdown_game.utils.LongestWordFinder;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.example.countdown_game.utils.LongestWordFinder.*;


/**
 * Service class that handles the core game logic for the Countdown Game.
 *
 * <p>This service provides functionality to generate vowels and consonants,
 * as well as validate user-submitted words based on the current game letters.</p>
 */
@Service
public class GameService {
    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    private static boolean isDictionaryLoaded = false;
    Random random = new Random();
    private static final List<Character> VOWELS = Arrays.asList('A', 'E', 'I', 'O', 'U');
    private static final List<Character> CONSONANTS = Arrays.asList(
            'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'
    );


    InputValidator inputValidator = new InputValidator();

    /**
     * Generates a specified number of random vowels.
     *
     * @return A list of randomly selected vowels
     */
    public Character generateVowels() {
        return VOWELS.get(random.nextInt(VOWELS.size()));
    }

    /**
     * Initializes the dictionary during application startup.
     * <p>
     * This method is executed automatically after the bean is constructed, loading the dictionary
     * into memory for subsequent use.
     * </p>
     */

    public void initializeDictionary() {
        if (!isDictionaryLoaded) {
            synchronized (GameService.class) {
                if (!isDictionaryLoaded) {
                    LongestWordFinder.loadDictionary();
                    isDictionaryLoaded = true;
                }
            }
        }
    }


    /**
     * Generates a specified number of random consonants.
     *
     * @return A list of randomly selected consonants
     */
    public Character generateConsonants() {

        return CONSONANTS.get(random.nextInt(CONSONANTS.size()));
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
        try {
            if (!isValidWord(word) || !inputValidator.validateInput(word)) {
                logger.warn("Word '{}' is not valid in the dictionary.", word);
                return false;
            }

            Map<Character, Integer> letterCount = new HashMap<>();

            for (char letter : letters.toCharArray()) {
                letterCount.put(letter, letterCount.getOrDefault(letter, 0) + 1);
            }

            for (char ch : word.toUpperCase().toCharArray()) {
                if (!letterCount.containsKey(ch) || letterCount.get(ch) == 0) {
                    logger.warn("Word '{}' cannot be formed with available letters.", word);
                    return false;
                }
                letterCount.put(ch, letterCount.get(ch) - 1);
            }

            logger.info("Word '{}' is valid with the given letters.", word);
            return true;
        } catch (Exception e) {
            logger.error("Error validating word '{}' with letters '{}': {}", word, letters, e.getMessage(), e);
            return false;
        }
    }

    public boolean isValidWord(String word) {
        return validateWordInLongestWordFinder(word);
    }

    public String findLongestWord(String word) throws IOException {
        return findLongestWordInLongestWordFinder(word);
    }
}
