package com.example.countdown_game.service;

import com.example.countdown_game.entity.Score;
import com.example.countdown_game.repository.ScoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Service class for handling score-related operations.
 * This class interacts with the {@link ScoreRepository} to manage score data
 * and provides methods to save and retrieve scores for players.
 */
@Service
@ComponentScan
public class ScoreService {

    private static final Logger logger = LoggerFactory.getLogger(ScoreService.class);

    private final ScoreRepository scoreRepository;

    /**
     * Constructor to initialize the ScoreService with the required {@link ScoreRepository}.
     *
     * @param scoreRepository the repository used for managing score data.
     */
    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    /**
     * Retrieves all scores for a specific player.
     *
     * @param playerName the name of the player whose scores are to be retrieved.
     * @return a list of {@link Score} objects associated with the given player.
     */
    public List<Score> getScoresForPlayer(String playerName) {
        try {
            logger.info("Retrieving scores for player: {}", playerName);
            return scoreRepository.findByPlayerName(playerName);
        } catch (Exception e) {
            logger.error("Failed to retrieve score for player: {}", playerName, e);
            return Collections.emptyList();
        }
    }

    /**
     * Saves a new score for a player.
     *
     * @param playerName     the name of the player.
     * @param currentLetters the letters selected during the game.
     * @param word           the word submitted by the player.
     * @param scoreValue     the score associated with the submitted word.
     */
    public boolean saveScore(String playerName, String currentLetters, String word,
                             int scoreValue, String longestPossibleWord) {

        try {
            logger.info("Saving score for player: {}", playerName);
            Score score = new Score( playerName, scoreValue, currentLetters, word, longestPossibleWord);
            scoreRepository.save(score);
            return true;
        } catch (Exception e) {
            logger.error("Failed to save score for player: {}", playerName, e);
            return false;
        }
    }

    /**
     * Reset all the game data
     */
    public void resetAllGameData() {
        // Assuming you have a method to delete game data by playerName
        scoreRepository.deleteAllScores();
    }
}
