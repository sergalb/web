package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.TableObject;

import java.sql.*;
import java.util.Date;
import java.util.List;

public interface CommonRepository {
    TableObject toTableObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;
    TableObject findByParams(String requestDB, Object[] parameters, String errorMessage);
    void insert(String requestDB, String tableName, TableObject tableObject, Object[] parameters);
    Date findCreationTime(long id, String tableName);
    List<TableObject> findAll(String requestDB, String tableName, Object[] parameters);
    void update(String requestDB, String tableName, Object[] parameters, String errorMessage);
}