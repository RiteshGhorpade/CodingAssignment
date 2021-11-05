package com.creditsuisse.assignment;

import com.creditsuisse.assignment.service.LogFileGetterService;
import com.creditsuisse.assignment.service.LogFileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class AssignmentApplication implements CommandLineRunner {

    @Autowired
    public LogFileGetterService LogFileGetterService;
    @Autowired
    public LogFileProcessor logFileProcessor;

    public static void main(String[] args) {
        SpringApplication.run(AssignmentApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        File logFile = LogFileGetterService.getFile();
        logFileProcessor.generateEvents(logFile);
    }
}
