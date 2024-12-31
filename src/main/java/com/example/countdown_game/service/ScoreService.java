package com.example.countdown_game.service;

import com.example.countdown_game.entity.Score;
import com.example.countdown_game.repository.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for handling score-related operations.
 * This class interacts with the {@link ScoreRepository} to manage score data
 * and provides methods to save and retrieve scores for players.
 */
@Service
public class ScoreService {

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
        return scoreRepository.findByPlayerName(playerName);
    }

    /**
     * Saves a new score for a player.
     *
     * @param playerName     the name of the player.
     * @param currentLetters the letters selected during the game.
     * @param word           the word submitted by the player.
     * @param scoreValue     the score associated with the submitted word.
     */
    public void saveScore(String playerName, String currentLetters, String word, int scoreValue) {

        Score score = new Score();
        score.setPlayerName(playerName);
        score.setAnswered(word);
        score.setSelectedAlphabet(currentLetters);
        score.setScoreValue(scoreValue);
        scoreRepository.save(score);
    }
}
