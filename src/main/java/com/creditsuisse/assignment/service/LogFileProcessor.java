package com.creditsuisse.assignment.service;

import com.creditsuisse.assignment.dao.ApplicationEvent;
import com.creditsuisse.assignment.dao.Event;
import com.creditsuisse.assignment.dao.SimpleEvent;
import com.creditsuisse.assignment.dto.LogEntry;
import com.creditsuisse.assignment.repository.ApplicationEventRepository;
import com.creditsuisse.assignment.repository.SimpleEventRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogFileProcessor {

    private final List<Event> listEvent = new ArrayList<>();
    private final List<Event> listApplicationEvent = new ArrayList<>();
    private final Map<String, LogEntry> mapIdLogEntry = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(LogFileProcessor.class);

    @Autowired
    private ApplicationEventRepository applicationEventRepository;
    @Autowired
    private SimpleEventRepository simpleEventRepository;

    public void generateEvents(File file) throws IOException {
        logger.info("Started Processing and Generating Required Events for File "+file.getAbsoluteFile().getAbsolutePath());
        processFile(file);
        processEvents();
        logger.info("Completed Processing File and Generating Required Events");
    }

    private void processFile(File file) throws IOException {
        Gson gson = new Gson();
        try (BufferedReader fileBufferReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = fileBufferReader.readLine()) != null && line != "") {
                LogEntry logEntry = gson.fromJson(line, LogEntry.class);
                logger.debug("Processing the following Entry "+ logEntry);
                String id = logEntry.getId();
                if (mapIdLogEntry.containsKey(id)) {
                    addEntryToList(logEntry, id);
                } else {
                    mapIdLogEntry.put(id, logEntry);
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("Not able to find the File",e);
            throw e;
        } catch (IOException e) {
            logger.error("IO exception",e);
            throw e;
        }
    }

    private void addEntryToList(LogEntry logEntry, String id) {
        LogEntry previousEntry = mapIdLogEntry.remove(id);
        long duration = Math.abs(logEntry.getTimestamp() - previousEntry.getTimestamp());
        if (duration > 4L) {
            if (logEntry.getType() == null && logEntry.getHost() == null) {
                Event simpleEvent = new SimpleEvent(id, duration);
                logger.debug("Adding The Following Event for Processing "+ simpleEvent);
                listEvent.add(simpleEvent);
            } else {
                Event appEvent = new ApplicationEvent(id, duration, logEntry.getHost(), logEntry.getType());
                logger.debug("Adding The Following APPEvent for Processing "+ appEvent);
                listApplicationEvent.add(appEvent);
            }
        }
    }

    private void processEvents() {
        listEvent.parallelStream().forEach(event -> {
            logger.debug("Before Saving the following Event in Database "+ event);
            simpleEventRepository.save((SimpleEvent) event);
            logger.info("Saved the following Event in Database "+ event);
        });
        listApplicationEvent.parallelStream().forEach(event -> {
            logger.debug("Before Saving the following APPEvent in Database "+ event);
            applicationEventRepository.save((ApplicationEvent) event);
            logger.info("Saved the following AppEvent in Database "+ event);
        });


    }

}
