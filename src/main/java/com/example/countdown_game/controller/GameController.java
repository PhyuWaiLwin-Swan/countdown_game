package com.example.countdown_game.controller;

import com.example.countdown_game.entity.Score;
import com.example.countdown_game.service.GameService;
import com.example.countdown_game.service.ScoreService;
import com.example.countdown_game.utils.LongestWordFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 *
 *
 * This controller provides endpoints for generating vowels, consonants, and validating words
 * based on the current game session.</p>
 */
@RestController
@ComponentScan
@RequestMapping("/api/game")
public class GameController {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    private final GameService gameService;
    private final ScoreService scoreService;


    /**
     * Constructs a GameController with the provided GameService.
     *
     * @param gameService  The service handling game-related logic
     * @param scoreService
     */
    public GameController(GameService gameService, ScoreService scoreService) {
        this.gameService = gameService;
        this.scoreService = scoreService;
        gameService.initializeDictionary();
    }

    /**
     * Generates a specified number of random vowels.
     *
     * <p>This endpoint allows the client to request a certain number of vowels
     * to be generated randomly from a predefined list.</p>
     *
     * @return A list of randomly selected vowels
     */
    @GetMapping("/vowels")
    public Character getVowels() {
        return gameService.generateVowels();

    }

    /**
     * Generates a specified number of random consonants.
     *
     * <p>This endpoint allows the client to request a certain number of consonants
     * to be generated randomly from a predefined list.</p>
     *
     * @return A list of randomly selected consonants
     */
    @GetMapping("/consonants")
    public Character getConsonants() {
        return gameService.generateConsonants();
    }

    /**
     * Validates a given word against the current set of letters.
     *
     * <p>This endpoint checks if the provided word is valid based on the current set of letters
     * available in the game session. If the word is valid, it calculates a score based on the word's length.</p>
     *
     * @param word          The word to validate
     * @param currentLetters The letters available in the game session, provided as a string
     * @return A map containing the word, its validity, and its score
     */
    @PostMapping("/validate")
    public Map<String, Object> validateWord(@RequestParam String word,
                                            @RequestParam String currentLetters,
                                            @RequestParam String playerName) {
        Map<String, Object> response = new HashMap<>();
        try {
            logger.info("Validating word: {}", word);
            // Find the longest word that can be formed from the current letters
            String longestWord = gameService.findLongestWord(currentLetters);
            boolean isValid = gameService.validateWord(word, currentLetters);
            int scoreValue = isValid ? word.length() : 0;




            // Save the score to the database
            scoreService.saveScore(playerName, currentLetters, word, scoreValue, longestWord);

            response.put("word", word);
            response.put("isValid", isValid);
            response.put("score", scoreValue);
            response.put("longestWord", longestWord);
            if (longestWord != null) {
                response.put("longestWordLength", longestWord.length());
            } else {
                response.put("longestWordLength", 0);
            }
        } catch (Exception e) {
            logger.error("Error validating word: {}", e.getMessage(), e);
            response.put("error", "An error occurred while validating the word. Please try again.");
        }
        return response;
    }
}
