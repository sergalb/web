package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.database.DatabaseUtils;
import ru.itmo.webmail.model.domain.TableObject;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.CommonRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

abstract class CommonRepositoryImpl implements CommonRepository{
    private static final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();
    public TableObject findByParams(String tableName, Pair[] parametrs) {
        StringBuilder requestDB = new StringBuilder();
        for (int i = 0; i < parametrs.length; ++i) {
            requestDB.append(parametrs[i].getName()).append("=?");
            if (i + 1 < parametrs.length) {
                requestDB.append(" AND ");
            }
        }
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " + tableName +" WHERE " + requestDB.toString())) {
                setStatement(statement, parametrs);
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
                    "SELECT creationTime FROM " + tableName+ " WHERE id=?")) {
                statement.setLong(1, id);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getTimestamp(1);
                    }
                }
                throw new RepositoryException("Can't findBy"+tableName+"Id " + tableName + ".creationTime by id.");
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't findBy"+tableName+"Id " + tableName + ".creationTime by id.");
        }
    }
    public void insert(String tableName, TableObject tableObject, Pair[] parametrs) {
        StringBuilder requestDB = new StringBuilder(" (");
        for (Pair parametr : parametrs) {
            requestDB.append(parametr.getName()).append(", ");
        }
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO "+ tableName + requestDB.toString() + "creationTime)" +
                            " VALUES ("+String.join("", Collections.nCopies(parametrs.length, "?, ")) + "NOW())",
                 Statement.RETURN_GENERATED_KEYS)) {
                setStatement(statement, parametrs);
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

    public void update(String tableName, PairOfPair parametrs) {
        StringBuilder requestDB = new StringBuilder();
        requestDB.append(parametrs.getValue().getName()).append("=?").append(" WHERE ")
               .append(parametrs.getKey().getName()).append("=?");

        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE "+ tableName + " SET " + requestDB, Statement.RETURN_GENERATED_KEYS)) {
                setStatement(statement, new Pair[] {parametrs.getValue(), parametrs.getKey()});
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't update " + parametrs.getValue().getName() + " in " + tableName + ".");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save " + tableName + ".", e);
        }
    }
    public List<TableObject> findAll(String tableName, String parameter) {
        List<TableObject> tableObjects = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " + tableName +" ORDER BY " + parameter)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        tableObjects.add(toTableObject(statement.getMetaData(), resultSet));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't findBy" + tableName + "Id all "+ tableName +"s.", e);
        }
        return tableObjects;
    }
    private void setStatement(PreparedStatement statement,  Pair[] parametrs) throws SQLException {
        for (int i = 0; i < parametrs.length; ++i) {
            Class<?> curClass = parametrs[i].getValue().getClass();
            if (curClass.equals(String.class)) {
                statement.setString(i+1, (String) parametrs[i].getValue());
            } else if (curClass.equals(Long.class)) {
                statement.setLong(i+1, (Long) parametrs[i].getValue());
            } else if (curClass.equals(Boolean.class)) {
                statement.setBoolean(i+1, (Boolean) parametrs[i].getValue());
            }
        }
    }
}