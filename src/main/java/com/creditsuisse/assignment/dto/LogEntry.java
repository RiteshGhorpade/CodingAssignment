package com.creditsuisse.assignment.dto;

public class LogEntry {
    private final String id;

    private final String state;

    private final Long timestamp;

    private final String type;

    private final String host;

    public LogEntry(String id, String state, Long timestamp, String type, String host) {
        this.id = id;
        this.state = state;
        this.timestamp = timestamp;
        this.type = type;
        this.host = host;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LogEntry{");
        sb.append("id='").append(id).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append(", type='").append(type).append('\'');
        sb.append(", host='").append(host).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

