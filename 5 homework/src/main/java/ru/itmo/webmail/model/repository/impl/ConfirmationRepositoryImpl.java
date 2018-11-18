package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.domain.Confirm;
import ru.itmo.webmail.model.domain.TableObject;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.ConfirmationRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ConfirmationRepositoryImpl extends CommonRepositoryImpl implements ConfirmationRepository {
    @Override
    public TableObject toTableObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        return toConfirm(metaData, resultSet);
    }

    public void setSecret(String secret, long userId) {
        Confirm confirm = new Confirm(secret, userId);
        super.insert("EmailConfirmation", confirm, new Pair[]{new Pair("userId", userId), new Pair("secret", secret)});
    }

    @Override
    public Confirm UserBySecret(String secret) {
        return (Confirm) super.findByParams("EmailConfirmation", new Pair[] {new Pair("secret", secret)});
    }


    private Confirm toConfirm(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Confirm event = new Confirm();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                event.setId(resultSet.getLong(i));
            } else if ("userId".equalsIgnoreCase(columnName)) {
                event.setUserId(resultSet.getLong(i));
            } else if ("secret".equalsIgnoreCase(columnName)) {
                event.setSecret(resultSet.getString(i));
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                event.setCreationTime(resultSet.getTimestamp(i));
            } else {
                throw new RepositoryException("Unexpected column 'EmailConfirmation." + columnName + "'.");
            }
        }
        return event;
    }

}
