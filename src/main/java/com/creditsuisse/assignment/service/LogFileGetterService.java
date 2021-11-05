package com.creditsuisse.assignment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Service
public class LogFileGetterService {
    private String logFilePath;
    private File file;
    private final Logger logger = LoggerFactory.getLogger(LogFileGetterService.class);

    public File getFile() {
        if (exists()) {
            return file;
        } else {
            boolean isNotFirstTry = false;
            String filePath = "";
            System.out.println("Do you want to  Specify The Path-> (Y/N) ");
            Scanner scanner = new Scanner(System.in);
            File logfile;
            do {
                if (isNotFirstTry) {
                    System.out.println("Please Specify  Correct Path -> (Y/N)");
                } else {
                    isNotFirstTry = true;
                }
                logfile = getFile(filePath, scanner);

            } while (logfile == null);
            return logfile;
        }
    }

    private File getFile(String filePath, Scanner scanner) {
        boolean isFilePathProvided = false;
        while (isFilePathProvided == false) {
            String pathSpecified = scanner.nextLine();
            switch (pathSpecified.toLowerCase()) {
                case "y": {
                    System.out.print("Kindly Provide the File Path : ");
                    filePath = scanner.nextLine();
                    isFilePathProvided = true;
                    break;
                }
                case "n": {
                    try {
                        logger.debug("Using the default classpath log file log.json");
                        File file = ResourceUtils.getFile("classpath:log.json");
                        if (file.exists()) {
                            return file;
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    isFilePathProvided = true;
                    break;
                }
                default:
                    System.out.println("Please Enter Y or N");
                    break;
            }
        }
        logger.debug("User Entered the Following Log File"+filePath);
        File logFile = new File(filePath);
        if (logFile.exists()) {
            return logFile;
        } else {
            return null;
        }
    }

    public boolean exists() {
        if (this.file != null && this.file.exists()) {
            return true;
        } else {
            return false;
        }
    }

}