package com.example.countdown_game.controller;

import com.example.countdown_game.entity.Score;
import com.example.countdown_game.service.ScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller class to handle API requests related to player scores.
 * Provides endpoints to retrieve scores for a specific player.
 */
@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    private final ScoreService scoreService;

    /**
     * Constructor to inject the {@link ScoreService}.
     *
     * @param scoreService the service used to manage scores.
     */
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    /**
     * Endpoint to retrieve scores for a specific player.
     *
     * @param playerName the name of the player whose scores are to be retrieved.
     * @return a {@link List} of {@link Score} objects for the specified player.
     */
    @GetMapping("/player")
    public List<Score> getScoresByPlayer(@RequestParam String playerName) {
        return scoreService.getScoresForPlayer(playerName);
    }
}
