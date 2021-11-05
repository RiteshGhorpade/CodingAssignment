package com.creditsuisse.assignment.service;

import com.creditsuisse.assignment.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogFileProcessorTest extends BaseIntegrationTest {
    @Autowired
    public LogFileProcessor logFileProcessor;

    @Test
    void generateEventsExceptionCheck() {
        assertThrows(FileNotFoundException.class, () -> {
            logFileProcessor.generateEvents(new File("temp"));
        });
    }
}