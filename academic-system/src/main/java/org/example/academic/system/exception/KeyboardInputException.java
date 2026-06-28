package org.example.academic.system.exception;

public class KeyboardInputException extends RuntimeException {
    public KeyboardInputException(String message) {
        super(message);
    }
    public KeyboardInputException(String message, Throwable cause) {
        super(message, cause);
    }
}