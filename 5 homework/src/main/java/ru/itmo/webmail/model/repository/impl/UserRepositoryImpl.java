package ru.itmo.webmail.model.repository.impl;

import javafx.util.Pair;
import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.TableObject;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserRepositoryImpl extends CommonRepositoryImpl implements UserRepository {
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public User find(long userId) {
        return (User) super.findByParams("User", new Pair[] {new Pair("id", userId)});
    }

    @Override
    public User findByLogin(String login) {
        return (User) super.findByParams("User", new Pair[] {new Pair("login", login)});
    }

    @Override
    public User findByEmail(String email) {
        return (User) super.findByParams("User", new Pair[] {new Pair("email", email)});
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        return (User) super.findByParams("User", new Pair[] {new Pair("login", login), new Pair("passwordSha", passwordSha)});
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha) {
        return (User) super.findByParams("User", new Pair[] {new Pair("email", email), new Pair("passwordSha", passwordSha)});
    }
    //todo вынести в общем виде в CommonRepositoryImp
    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        List<TableObject> convert = super.findAll("User", "id");
        for (TableObject aConvert : convert) {
            users.add((User) aConvert);
        }
        return  users;
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
            }else if ("email".equalsIgnoreCase(columnName)) {
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
        super.insert("User", user, new Pair[] {new Pair("login", user.getLogin()),
                new Pair("email", email), new Pair("passwordSha", passwordSha)});
    }

    private Date findCreationTime(long userId) {
        return super.findCreationTime(userId, "User");
    }

    @Override
    public TableObject toTableObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        return toUser(metaData, resultSet);
    }
}