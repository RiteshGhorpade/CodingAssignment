package com.creditsuisse.assignment.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "APPLICATION_EVENT")
public class ApplicationEvent implements Event {
    @Id
    private String id;
    private long duration;
    private String host;

    public ApplicationEvent() {

    }

    public ApplicationEvent(String id, long duration, String host, String type) {
        this.id = id;
        this.duration = duration;
        this.host = host;
        this.type = type;
    }

    private String type;

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
        final StringBuilder sb = new StringBuilder("ApplicationEvent{");
        sb.append("id=").append(id);
        sb.append(", duration=").append(duration);
        sb.append(", host='").append(host).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
