package com.example.countdown_game.utils;

import com.example.countdown_game.controller.GameController;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

/**
 * Utility class to find the longest word from a set of letters and validate words.
 * <p>
 * This class includes methods for loading a dictionary, generating permutations of letters,
 * and finding or validating words based on the dictionary.
 * </p>
 */
@Component
public class LongestWordFinder {
    private static final Logger logger = LoggerFactory.getLogger(LongestWordFinder.class);

    /**
     * A set to store the dictionary words for fast lookup.
     */
    private static final Set<String> dictionary = new HashSet<>();

    /**
     * Initializes the dictionary during application startup.
     * <p>
     * This method is executed automatically after the bean is constructed, loading the dictionary
     * into memory for subsequent use.
     * </p>
     */
    @PostConstruct
    public static void initializeDictionary() {
        try {
            loadDictionary();
            logger.info("Dictionary loaded successfully.");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load dictionary", e);
        }
    }

    /**
     * Loads the dictionary from a file into memory.
     * <p>
     * The dictionary file is expected to be located at the specified relative path.
     * </p>
     *
     * @throws IOException if there is an error reading the dictionary file.
     */
    private static void loadDictionary() throws IOException {
        // Define the relative path to the file
        String filePath = "src/main/java/com/example/countdown_game/utils/words_alpha.txt";

        // Create a File object for the path
        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String word;
            while ((word = br.readLine()) != null) {
                dictionary.add(word.trim().toLowerCase());
            }
        }
    }

    /**
     * Validates if the given word exists in the dictionary.
     *
     * @param word the word to validate.
     * @return {@code true} if the word exists in the dictionary, {@code false} otherwise.
     */
    public static Boolean validateWordInLongestWordFinder(String word) {
        return dictionary.contains(word);
    }

    /**
     * Finds the longest valid word that can be formed using the given letters.
     *
     * @param letters a string containing the available letters.
     * @return the longest valid word, or {@code null} if no valid word is found.
     * @throws IOException if the dictionary is not loaded.
     */
    public static String findLongestWordInLongestWordFinder(String letters) throws IOException {
        if (dictionary.isEmpty()) {
            throw new IllegalStateException("Dictionary is not loaded.");
        }

        char[] letterArray = letters.toLowerCase(Locale.ROOT).toCharArray();
        Arrays.sort(letterArray); // Sort to handle permutations efficiently

        // Try permutations of lengths from max to 1
        for (int len = letterArray.length; len > 0; len--) {
            List<String> permutations = generatePermutations(letterArray, len);

            // Check each permutation for a valid word
            for (String word : permutations) {
                if (dictionary.contains(word)) {
                    return word;
                }
            }
        }

        return null; // No word found
    }

    /**
     * Generates all permutations of the given letters for a specific length.
     *
     * @param letters an array of characters.
     * @param length the desired length of permutations.
     * @return a list of permutations.
     */
    private static List<String> generatePermutations(char[] letters, int length) {
        List<String> result = new ArrayList<>();
        permute(letters, 0, length, result);
        return result;
    }

    /**
     * Recursive helper to generate permutations of letters.
     *
     * @param letters an array of characters.
     * @param start the starting index for permutations.
     * @param length the desired length of permutations.
     * @param result the list to store generated permutations.
     */
    private static void permute(char[] letters, int start, int length, List<String> result) {
        if (start == length) {
            result.add(new String(Arrays.copyOf(letters, length)));
            return;
        }

        for (int i = start; i < letters.length; i++) {
            if (shouldSwap(letters, start, i)) {
                swap(letters, start, i);
                permute(letters, start + 1, length, result);
                swap(letters, start, i); // Backtrack
            }
        }
    }

    /**
     * Determines if characters should be swapped to avoid duplicate permutations.
     *
     * @param letters an array of characters.
     * @param start the starting index for permutations.
     * @param current the current index.
     * @return {@code true} if swapping is allowed, {@code false} otherwise.
     */
    private static boolean shouldSwap(char[] letters, int start, int current) {
        for (int i = start; i < current; i++) {
            if (letters[i] == letters[current]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Swaps two elements in an array.
     *
     * @param arr the array of characters.
     * @param i the first index.
     * @param j the second index.
     */
    private static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
