package ru.itmo.webmail.model.domain;

import java.io.Serializable;
import java.util.Date;

public class User extends Domain{
    private String login;
    private String email;
    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
}
