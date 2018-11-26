package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.domain.Event;
import ru.itmo.webmail.model.domain.TableObject;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.EventRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;

public class EventRepositoryImpl extends CommonRepositoryImpl implements EventRepository {

    @Override
    public void doEvent(long userId, String type) {
        Event event = new Event(userId, type);
        super.insert("INSERT INTO Event (userId, type, creationTime) VALUES (?, ?, NOW())",
                "Event", event, new Object[]{userId, type});
    }

    private Date findCreationTime(long eventId) {
        return super.findCreationTime(eventId, "Event");
    }

    private Event toEvent(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                event.setId(resultSet.getLong(i));
            } else if ("userId".equalsIgnoreCase(columnName)) {
                event.setUserId(resultSet.getLong(i));
            } else if ("type".equalsIgnoreCase(columnName)) {
                event.setType(resultSet.getString(i));
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                event.setCreationTime(resultSet.getTimestamp(i));
            } else {
                throw new RepositoryException("Unexpected column 'Event." + columnName + "'.");
            }
        }
        return event;
    }

    @Override
    public TableObject toTableObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        return toEvent(metaData, resultSet);
    }
}
