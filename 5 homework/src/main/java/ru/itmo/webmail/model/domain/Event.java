package ru.itmo.webmail.model.domain;

public class Event extends Domain {
    private long userId;
    private String type;

    public Event(long userId, String type) {
        this.userId = userId;
        this.type = type;
    }
    public Event(){};
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
