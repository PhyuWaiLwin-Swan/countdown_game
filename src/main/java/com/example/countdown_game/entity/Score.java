package com.example.countdown_game.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entity class representing a Score in the Countdown Game.
 * Stores details about a player's score, the word they answered with, and the selected letters.
 */
@Entity
public class Score {

    /**
     * The unique identifier for the score record.
     * It is auto-generated using {@link GenerationType#IDENTITY}.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the player associated with this score.
     */
    private String playerName;

    /**
     * The score achieved by the player.
     */
    private int scoreValue;

    /**
     * The selected letters used during the game round.
     */
    private String selectedAlphabet;

    /**
     * The word answered by the player.
     */
    private String answered;

    /**
     * Parameterized constructor for creating a Score object with all fields.
     *
     * @param playerName      the name of the player
     * @param score           the score of the player
     * @param selectedAlphabet the letters selected during the game
     * @param answered        the word answered by the player
     */
    public Score(String playerName, int score, String selectedAlphabet, String answered) {
        this.playerName = playerName;
        this.scoreValue = score;
        this.selectedAlphabet = selectedAlphabet;
        this.answered = answered;
    }

    public Score() {

    }

    // Getters and Setters

    /**
     * Gets the name of the player.
     *
     * @return the player's name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the name of the player.
     *
     * @param playerName the player's name.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Gets the score achieved by the player.
     *
     * @return the player's score.
     */
    public int getScoreValue() {
        return scoreValue;
    }

    /**
     * Sets the score achieved by the player.
     *
     * @param score the player's score.
     */
    public void setScoreValue(int score) {
        this.scoreValue = score;
    }

    /**
     * Gets the word answered by the player.
     *
     * @return the player's answered word.
     */
    public String getAnswered() {
        return answered;
    }

    /**
     * Sets the word answered by the player.
     *
     * @param answered the player's answered word.
     */
    public void setAnswered(String answered) {
        this.answered = answered;
    }

    /**
     * Gets the selected letters used during the game round.
     *
     * @return the selected letters.
     */
    public String getSelectedAlphabet() {
        return selectedAlphabet;
    }

    /**
     * Sets the selected letters used during the game round.
     *
     * @param selectedAlphabet the selected letters.
     */
    public void setSelectedAlphabet(String selectedAlphabet) {
        this.selectedAlphabet = selectedAlphabet;
    }
}
