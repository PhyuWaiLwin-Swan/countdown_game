package com.example.countdown_game.controller;

import com.example.countdown_game.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST controller for managing game-related endpoints in the Countdown Game application.
 *
 * <p>This controller provides endpoints for generating vowels, consonants, and validating words
 * based on the current game session.</p>
 */
@RestController
@RequestMapping("/api/game")
public class GameController {
    private final GameService gameService;

    /**
     * Constructs a GameController with the provided GameService.
     *
     * @param gameService The service handling game-related logic
     */
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Generates a specified number of random vowels.
     *
     * <p>This endpoint allows the client to request a certain number of vowels
     * to be generated randomly from a predefined list.</p>
     *
     * @param count The number of vowels to generate (default is 3)
     * @return A list of randomly selected vowels
     */
    @GetMapping("/vowels")
    public List<Character> getVowels(@RequestParam(defaultValue = "3") int count) {
        return gameService.generateVowels(count);
    }

    /**
     * Generates a specified number of random consonants.
     *
     * <p>This endpoint allows the client to request a certain number of consonants
     * to be generated randomly from a predefined list.</p>
     *
     * @param count The number of consonants to generate (default is 6)
     * @return A list of randomly selected consonants
     */
    @GetMapping("/consonants")
    public List<Character> getConsonants(@RequestParam(defaultValue = "6") int count) {
        return gameService.generateConsonants(count);
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
    public Map<String, Object> validateWord(@RequestParam String word, @RequestParam String currentLetters) {
        boolean isValid = gameService.validateWord(word, currentLetters);
        Map<String, Object> response = new HashMap<>();
        response.put("word", word);
        response.put("isValid", isValid);
        response.put("score", isValid ? word.length() : 0);
        return response;
    }
}
