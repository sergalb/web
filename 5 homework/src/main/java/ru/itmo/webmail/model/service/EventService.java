package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.repository.impl.EventRepositoryImpl;

public class EventService {
    private EventRepositoryImpl eventRepository = new EventRepositoryImpl();
    public void doEvent(long userId, String type) {
        eventRepository.doEvent(userId, type);
    }
}