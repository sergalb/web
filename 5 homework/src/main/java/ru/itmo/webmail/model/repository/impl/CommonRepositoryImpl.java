package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.TableObject;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.CommonRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

abstract class CommonRepositoryImpl implements CommonRepository {
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    public TableObject findByParams(String requestDB, String tableName, Object[] parameters) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(requestDB)) {
                setStatement(statement, parameters);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return toTableObject(statement.getMetaData(), resultSet);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't findBy" + tableName + "Id" + tableName + " by id.", e);
        }
    }

    public Date findCreationTime(long id, String tableName) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT creationTime FROM " + tableName + " WHERE id=?")) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getTimestamp(1);
                    }
                }
                throw new RepositoryException("Can't findBy" + tableName + "Id " + tableName + ".creationTime by id.");
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't findBy" + tableName + "Id " + tableName + ".creationTime by id.");
        }
    }

    public void insert(String requestDB, String tableName, TableObject tableObject, Object[] parameters) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    requestDB, Statement.RETURN_GENERATED_KEYS)) {
                setStatement(statement, parameters);
                if (statement.executeUpdate() == 1) {
                    ResultSet generatedIdResultSet = statement.getGeneratedKeys();
                    if (generatedIdResultSet.next()) {
                        tableObject.setId(generatedIdResultSet.getLong(1));
                        tableObject.setCreationTime(findCreationTime(tableObject.getId(), tableName));
                    } else {
                        throw new RepositoryException("Can't findBy" + tableName + "Id id of saved " + tableName + ".");
                    }
                } else {
                    throw new RepositoryException("Can't save " + tableName + ".");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save " + tableName + ".", e);
        }
    }

    public void update(String requestDB, String tableName, Object[] parameters) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    requestDB, Statement.RETURN_GENERATED_KEYS)) {
                setStatement(statement, parameters);
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't update " + "parameters.getValue().getName()" + " in " + tableName + ".");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save " + tableName + ".", e);
        }
    }

    public List<TableObject> findAll(String requestDB, String tableName, Object[] parameters) {
        List<TableObject> tableObjects = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(requestDB)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        tableObjects.add(toTableObject(statement.getMetaData(), resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't findBy" + tableName + "Id all " + tableName + "s.", e);
        }
        return tableObjects;
    }

    private void setStatement(PreparedStatement statement, Object[] parameters) throws SQLException {
        for (int i = 0; i < parameters.length; ++i) {
            Class<?> curClass = parameters[i].getClass();
            if (curClass.equals(String.class)) {
                statement.setString(i + 1, (String) parameters[i]);
            } else if (curClass.equals(Long.class)) {
                statement.setLong(i + 1, (Long) parameters[i]);
            } else if (curClass.equals(Boolean.class)) {
                statement.setBoolean(i + 1, (Boolean) parameters[i]);
            }
        }
    }
}