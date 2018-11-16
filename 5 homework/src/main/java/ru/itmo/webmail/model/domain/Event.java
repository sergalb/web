package ru.itmo.webmail.model.domain;

public class Event extends Domain {
    private long UserId;
    private String type;

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
