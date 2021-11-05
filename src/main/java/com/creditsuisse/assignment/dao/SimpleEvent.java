package com.creditsuisse.assignment.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EVENT")
public class SimpleEvent implements Event {
    @Id
    private String id;
    private long duration;

    public SimpleEvent() {

    }

    public SimpleEvent(String id, long duration) {
        this.id = id;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SimpleEvent{");
        sb.append("id=").append(id);
        sb.append(", duration=").append(duration);
        sb.append('}');
        return sb.toString();
    }
}
