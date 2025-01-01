package com.example.countdown_game.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing game-related endpoints in the Countdown Game application.
 */
@RestController
@RequestMapping("/api/game")
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @GetMapping("/")
    public String home() {
        try {
            logger.info("Fetching home screen");
            return "index"; // Renders the home view
        } catch (Exception e) {
            logger.error("Error fetching home screen: {}", e.getMessage(), e);
            return "error"; // Return a generic error view or message
        }
    }
}
