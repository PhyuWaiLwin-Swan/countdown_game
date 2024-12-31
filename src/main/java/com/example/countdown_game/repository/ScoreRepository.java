package com.example.countdown_game.repository;

import com.example.countdown_game.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link Score} entities.
 * Extends {@link JpaRepository} to provide CRUD operations and custom query methods.
 */
public interface ScoreRepository extends JpaRepository<Score, Long> {

    /**
     * Retrieves a list of scores for a specific player based on their name.
     *
     * @param playerName the name of the player whose scores are to be retrieved.
     * @return a {@link List} of {@link Score} objects associated with the specified player name.
     */
    List<Score> findByPlayerName(String playerName);
}
