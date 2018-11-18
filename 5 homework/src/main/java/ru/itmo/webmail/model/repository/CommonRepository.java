package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.TableObject;

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

    class PairOfPair {
        private Pair key;
        private Pair value;

        public Pair getValue() {
            return value;
        }

        public void setValue(Pair value) {
            this.value = value;
        }

        public Pair getKey() {
            return key;
        }

        public void setKey(Pair key) {
            this.key = key;
        }

        public PairOfPair(Pair key, Pair value) {
            this.key = key;
            this.value = value;
        }
    }

    TableObject toTableObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;

    TableObject findByParams(String tableName, Pair[] parametrs);

    void insert(String tableName, TableObject tableObject, Pair[] parametrs);

    Date findCreationTime(long id, String tableName);

    List<TableObject> findAll(String tableName, String parameter);

    void update(String tableName, PairOfPair parametrs);
}