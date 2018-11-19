package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.domain.TableObject;
import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.RepositoryException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TalkRepositoryImpl extends CommonRepositoryImpl {
    public List<Talk> findAllTalksByUser(long userId) {
        List<Talk> talks = new ArrayList<>();
        List<TableObject> convert = super.findAll("SELECT * FROM Talk WHERE sourceUserId=? OR targetUserId=?",
                "Talk", new Object[] {userId});
        for (TableObject aConvert : convert) {
            talks.add((Talk) aConvert);
        }
        return talks;
    }

    @Override
    public TableObject toTableObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        return toTalk(metaData, resultSet);
    }

    private Talk toTalk(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                talk.setId(resultSet.getLong(i));
            } else if ("sourceUserId".equalsIgnoreCase(columnName)) {
                talk.setSourceUserId(resultSet.getLong(i));
            } else if ("targetUserId".equalsIgnoreCase(columnName)) {
                talk.setTargetUserId(resultSet.getLong(i));
            } else if ("text".equalsIgnoreCase(columnName)) {
                talk.setText(resultSet.getString(i));
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                talk.setCreationTime(resultSet.getTimestamp(i));
            } else {
                throw new RepositoryException("Unexpected column 'Talk." + columnName + "'.");
            }
        }
        return talk;
    }
}
