package com.example.countdown_game.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing game-related endpoints in the Countdown Game application.
 */
@RestController
@RequestMapping("/api/game")
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index"; // Renders
    }
}
