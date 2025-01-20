package com.example.countdown_game.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class representing a Score in the Countdown Game.
 * Stores details about a player's score, the word they answered with, and the selected letters.
 */
@Entity
@Table(name = "score")
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
    @Column(name = "player_name", nullable = false)
    private String playerName;

    /**
     * The score achieved by the player.
     */
    @Column(name = "score_value", nullable = false)
    private int scoreValue;

    /**
     * The selected letters used during the game round.
     */
    @Column(name = "selected_alphabet", nullable = false)
    private String selectedAlphabet;

    /**
     * The word answered by the player.
     */
    @Column(name = "answered", nullable = false)
    private String answered;

    /**
     * The longest possible word for the game round.
     */
    @Column(name = "longest_possible_word", nullable = false)
    private String longestPossibleWord;

    /**
     * Parameterized constructor for creating a Score object with all fields.
     *
     * @param playerName      the name of the player
     * @param score           the score of the player
     * @param selectedAlphabet the letters selected during the game
     * @param answered        the word answered by the player
     */
    public Score(String playerName, int score, String selectedAlphabet, String answered, String longestPossibleWord) {
        this.playerName = playerName;
        this.scoreValue = score;
        this.selectedAlphabet = selectedAlphabet;
        this.answered = answered;
        this.longestPossibleWord = longestPossibleWord;
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
     * Gets the longest possible word for a given round.
     *
     * @return the longest possible word.
     */
    public String getLongestPossibleWord() {
        return longestPossibleWord;
    }

    /**
     * Sets the longest possible word for a given round.
     *
     * @param longestPossibleWord the longest word to set.
     */
    public void setLongestPossibleWord(String longestPossibleWord) {
        this.longestPossibleWord = longestPossibleWord;
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
