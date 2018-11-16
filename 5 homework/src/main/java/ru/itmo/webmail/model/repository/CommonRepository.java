package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.TableObject;
import ru.itmo.webmail.model.exception.RepositoryException;

import java.sql.*;
import java.util.Date;
import java.util.List;

public interface CommonRepository {
    class Pair {
        private String name;
        private Object value;

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }

        public Pair(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }

    TableObject toTableObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;
    TableObject findByParams(String tableName, Pair[] parametrs);
    void insert(String tableName, TableObject tableObject, Pair[] parametrs);
    Date findCreationTime(long id, String tableName);
    List<TableObject> findAll(String tableName, String parameter);
}