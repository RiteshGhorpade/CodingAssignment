package com.creditsuisse.assignment.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Service
public class LogFileGetterService {
    private String logFilePath;
    private File file;

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