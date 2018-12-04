package ru.itmo.webmail.model.domain;

public class Article extends Domain{
    private long userId;
    private String title;
    private String text;
    private String login;
    private boolean hidden = false;

    public Article() {}
    public Article(long userId, String title, String text) {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

    public boolean isHidden() {
        return hidden;
    }
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
