package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Confirm;

public interface ConfirmationRepository {
    void setSecret(String secret, long userId);
    Confirm UserBySecret(String secret);
}