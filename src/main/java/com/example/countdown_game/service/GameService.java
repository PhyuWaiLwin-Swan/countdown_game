package com.example.countdown_game.service;


import com.example.countdown_game.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Service class that handles the core game logic for the Countdown Game.
 *
 * <p>This service provides functionality to generate vowels and consonants,
 * as well as validate user-submitted words based on the current game letters.</p>
 */
@Service
public class GameService {
    private static final Logger logger = LoggerFactory.getLogger(GameService.class);
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
        logger.info("Generated vowel: {}", vowels);
        return vowels;
    }
    /**
     * Checks if a given word is valid by verifying its existence in a dictionary API.
     *
     * @param word The word to validate.
     * @return {@code true} if the word exists in the dictionary API, {@code false} otherwise.
     * @throws RuntimeException If there is an issue with the dictionary API request (e.g., network error or invalid response).
     */
    public boolean isValidWord(String word) {
        logger.info("Validating word: {}", word);
        try {
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = Config.getApiUrl();
            String url = apiUrl + word.toLowerCase();

            logger.debug("Sending request to dictionary API: {}", url);
            restTemplate.getForObject(url, Object.class);

            logger.info("Word '{}' is valid", word);
            return true;
        } catch (Exception e) {
            logger.warn("Validation failed for word '{}': {}", word, e.getMessage());
            return false;
        }
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

        logger.info("Consonants generated: {}", consonants);
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
        logger.info("Validating word '{}' against letters '{}'", word, letters);

        // Check if the word is in the dictionary
        if (!isValidWord(word)) {
            logger.warn("Word '{}' is not found in the dictionary", word);
            return false;
        }

        Map<Character, Integer> letterCount = new HashMap<>();
        for (char letter : letters.toCharArray()) {
            letterCount.put(letter, letterCount.getOrDefault(letter, 0) + 1);
        }
        logger.debug("Letter count map: {}", letterCount);

        for (char ch : word.toUpperCase().toCharArray()) {
            if (!letterCount.containsKey(ch) || letterCount.get(ch) == 0) {
                logger.warn("Word '{}' cannot be formed using the provided letters '{}'", word, letters);
                return false;
            }
            letterCount.put(ch, letterCount.get(ch) - 1);
        }

        logger.info("Word '{}' is valid based on the provided letters '{}'", word, letters);
        return true;
    }
}