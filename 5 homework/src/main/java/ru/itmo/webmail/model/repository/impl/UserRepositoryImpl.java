package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.domain.TableObject;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl extends CommonRepositoryImpl implements UserRepository {
    @Override
    public User find(long userId) {
        return (User) super.findByParams("SELECT * FROM User WHERE id=?", "User",
                new Object[]{userId}, "User with id =" + userId + " doesn't exist");
    }

    @Override
    public User findByLogin(String login) {
        return (User) super.findByParams("SELECT * FROM User WHERE login=?", "User",
                new Object[]{login},"User with login =" + login + " doesn't exist");
    }

    @Override
    public User findByEmail(String email) {
        return (User) super.findByParams("SELECT * FROM User WHERE email=?", "User",
                new Object[]{email}, "User with email =" + email + " doesn't exist");
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) {
        return (User) super.findByParams("SELECT * FROM User WHERE login=? OR email=? AND passwordSha=?",
                "User", new Object[]{loginOrEmail, loginOrEmail, passwordSha},  "User with this Login/Email and password doesn't exist");
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        List<TableObject> convert = super.findAll("SELECT * FROM User ORDER BY id", "User", new Object[] {});
        for (TableObject aConvert : convert) {
            users.add((User) aConvert);
        }
        return users;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private User toUser(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                user.setId(resultSet.getLong(i));
            } else if ("login".equalsIgnoreCase(columnName)) {
                user.setLogin(resultSet.getString(i));
            } else if ("email".equalsIgnoreCase(columnName)) {
                user.setEmail(resultSet.getString(i));
            } else if ("passwordSha".equalsIgnoreCase(columnName)) {
                // No operations.
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                user.setCreationTime(resultSet.getTimestamp(i));
            } else if ("confirmed".equalsIgnoreCase(columnName)) {
                user.setConfirmed(resultSet.getBoolean(i));
            } else {
                throw new RepositoryException("Unexpected column 'User." + columnName + "'.");
            }
        }
        return user;
    }

    @Override
    public void save(User user, String passwordSha, String email) {
        super.insert("INSERT INTO User (login, email, passwordSha, creationTime) VALUES (?, ?, ?, NOW())", "User", user,
                new Object[]{user.getLogin(), email, passwordSha});
    }

    @Override
    public void confirmed(long userId) {
        super.update("UPDATE User SET confirmed=? WHERE id=?",
                "User", new Object[]{true, userId}, "Cant't confirmed User with id = " + userId);
    }

    @Override
    public TableObject toTableObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        return toUser(metaData, resultSet);
    }
}