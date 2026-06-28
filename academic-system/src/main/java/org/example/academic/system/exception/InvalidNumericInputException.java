package org.example.academic.system.exception;

public class InvalidNumericInputException extends KeyboardInputException {
    public InvalidNumericInputException(String message) {
        super(message);
    }
    public InvalidNumericInputException(String message, Throwable cause) {
        super(message, cause);
    }
}