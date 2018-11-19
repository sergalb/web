package ru.itmo.webmail.model.domain;

public class Confirm extends Domain {
    private String secret;
    private long userId;

    public Confirm(String secret, long userId) {
        this.secret = secret;
        this.userId = userId;
    }
    public Confirm() {
    }
    public String getSecret() {
        return secret;
    }
    public void setSecret(String secret) {
        this.secret = secret;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
}
