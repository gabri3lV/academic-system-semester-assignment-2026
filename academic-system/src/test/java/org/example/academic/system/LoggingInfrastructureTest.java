package org.example.academic.system;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class LoggingInfrastructureTest {

    @Test
    void loggerInstanceShouldBeCreatedSuccessfully() {
        Logger logger = LoggerFactory.getLogger(LoggingInfrastructureTest.class);
        assertNotNull(logger);
    }

    @Test
    void logMessagesShouldNotThrowExceptions() {
        Logger logger = LoggerFactory.getLogger(LoggingInfrastructureTest.class);
        assertDoesNotThrow(() -> {
            logger.info("Test info message");
            logger.warn("Test warn message");
            logger.error("Test error message");
        });
    }
}