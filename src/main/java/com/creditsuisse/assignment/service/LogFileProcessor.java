package com.creditsuisse.assignment.service;

import com.creditsuisse.assignment.dao.ApplicationEvent;
import com.creditsuisse.assignment.dao.Event;
import com.creditsuisse.assignment.dao.SimpleEvent;
import com.creditsuisse.assignment.dto.LogEntry;
import com.creditsuisse.assignment.repository.ApplicationEventRepository;
import com.creditsuisse.assignment.repository.SimpleEventRepository;
import com.google.gson.Gson;
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

    @Autowired
    private ApplicationEventRepository applicationEventRepository;
    @Autowired
    private SimpleEventRepository simpleEventRepository;

    public void generateEvents(File file) {
        processFile(file);
        processEvents();
    }

    private void processFile(File file) {
        Gson gson = new Gson();
        try (BufferedReader fileBufferReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = fileBufferReader.readLine()) != null && line != "") {
                LogEntry logEntry = gson.fromJson(line, LogEntry.class);
                String id = logEntry.getId();
                if (mapIdLogEntry.containsKey(id)) {
                    addEntryToList(logEntry, id);
                } else {
                    mapIdLogEntry.put(id, logEntry);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addEntryToList(LogEntry logEntry, String id) {
        LogEntry previousEntry = mapIdLogEntry.remove(id);
        long duration = Math.abs(logEntry.getTimestamp() - previousEntry.getTimestamp());
        if (duration > 4L) {
            if (logEntry.getType() == null && logEntry.getHost() == null) {
                Event simpleEvent = new SimpleEvent(id, duration);
                listEvent.add(simpleEvent);
            } else {
                Event appEvent = new ApplicationEvent(id, duration, logEntry.getHost(), logEntry.getType());
                listApplicationEvent.add(appEvent);
            }
        }
    }

    private void processEvents() {
        listEvent.parallelStream().forEach(event -> simpleEventRepository.save((SimpleEvent) event));
        listApplicationEvent.parallelStream().forEach(event -> applicationEventRepository.save((ApplicationEvent) event));
    }

}
