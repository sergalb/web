package ru.itmo.webmail.model.domain;

public class Talk extends Domain {
    private long sourceUserId;
    private long targetUserId;
    private String sourceUserLogin;
    private String targetUserLogin;
    public String getSourceUserLogin() {
        return sourceUserLogin;
    }
    public void setSourceUserLogin(String sourceUserLogin) {
        this.sourceUserLogin = sourceUserLogin;
    }
    public String getTargetUserLogin() {
        return targetUserLogin;
    }
    public void setTargetUserLogin(String targetUserLogin) {
        this.targetUserLogin = targetUserLogin;
    }
    private String text;
    public long getSourceUserId() {
        return sourceUserId;
    }
    public void setSourceUserId(long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }
    public long getTargetUserId() {
        return targetUserId;
    }
    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
