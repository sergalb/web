package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Event;

public interface EventRepository extends CommonRepository{

    void doEvent(long userId, String type);
}
