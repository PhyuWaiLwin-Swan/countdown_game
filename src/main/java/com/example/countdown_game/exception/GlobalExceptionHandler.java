package com.example.countdown_game.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * GlobalExceptionHandler is a centralized exception handling class for managing exceptions
 * across the application. It uses Spring's {@link ControllerAdvice} to intercept and handle
 * exceptions thrown by controllers and provide appropriate HTTP responses.
 *
 * <p>This class defines specific handlers for common exceptions like {@link IllegalArgumentException}
 * and a general handler for all other exceptions. It logs the errors using {@link Logger} to provide
 * debugging information.
 *
 * <p>By using this global exception handler, the application can ensure consistent error responses
 * and improve maintainability.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles all unexpected exceptions that occur in the application.
     *
     * <p>This method logs the exception details at the error level and returns a generic
     * HTTP 500 (Internal Server Error) response with a user-friendly error message.
     *
     * @param e The exception that was thrown.
     * @return A {@link ResponseEntity} containing the HTTP status and a generic error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger.error("Unexpected error occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred. Please try again later.");
    }

    /**
     * Handles {@link IllegalArgumentException} exceptions thrown in the application.
     *
     * <p>This method logs the exception details at the warning level and returns an
     * HTTP 400 (Bad Request) response with the exception message as the body.
     *
     * @param e The {@link IllegalArgumentException} that was thrown.
     * @return A {@link ResponseEntity} containing the HTTP status and the exception message.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        logger.warn("Invalid input: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
