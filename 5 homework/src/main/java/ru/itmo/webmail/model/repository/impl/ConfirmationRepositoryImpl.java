package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.domain.Confirm;
import ru.itmo.webmail.model.domain.TableObject;
import ru.itmo.webmail.model.repository.ConfirmationRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ConfirmationRepositoryImpl extends CommonRepositoryImpl implements ConfirmationRepository {
    @Override
    public TableObject toTableObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        return null;
    }
    public void setSecret(String secret, long userId) {
        Confirm confirm = new Confirm(secret, userId);
        super.insert("EmailConfirmation", confirm, new Pair[]{new Pair("userId", userId), new Pair("secret", secret)});
    }
}
