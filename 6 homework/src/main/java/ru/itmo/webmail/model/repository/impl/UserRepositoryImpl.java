package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.domain.TableObject;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl extends CommonRepositoryImpl implements UserRepository {
    @Override
    public User find(long userId) {
        return (User) super.findByParams("SELECT * FROM User WHERE id=?",
                new Object[]{userId}, "User with id =" + userId + " doesn't exist");
    }

    @Override
    public User findByLogin(String login) {
        return (User) super.findByParams("SELECT * FROM User WHERE login=?",
                new Object[]{login},"User with login =" + login + " doesn't exist");
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM User WHERE login=? AND passwordSha=?")) {
                statement.setString(1, login);
                statement.setString(2, passwordSha);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return toUser(statement.getMetaData(), resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User by id and passwordSha.", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        return (User) super.findByParams("SELECT * FROM User WHERE email=?",
                new Object[]{email}, "User with email =" + email + " doesn't exist");
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) {
        return (User) super.findByParams("SELECT * FROM User WHERE login=? OR email=? AND passwordSha=?",
                new Object[]{loginOrEmail, loginOrEmail, passwordSha},  "User with this Login/Email and password doesn't exist");
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
    public TableObject toTableObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        return toUser(metaData, resultSet);
    }
}