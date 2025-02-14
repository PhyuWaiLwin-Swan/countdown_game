package com.example.countdown_game.repository;

import com.example.countdown_game.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * Deletes all from the database.
     */
    void deleteAll();

    /**
     * Delete all records from score table.
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM Score")
    void deleteAllScores();
}
