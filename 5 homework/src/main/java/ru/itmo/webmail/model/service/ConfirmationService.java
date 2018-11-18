package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Confirm;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.repository.CommonRepository;
import ru.itmo.webmail.model.repository.ConfirmationRepository;
import ru.itmo.webmail.model.repository.impl.ConfirmationRepositoryImpl;

import java.util.Random;

public class ConfirmationService {
    private ConfirmationRepository confirmationRepository = new ConfirmationRepositoryImpl();
    void makeSecret(String email, long userId) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(7);
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        confirmationRepository.setSecret(sb.toString(), userId);
        sendMessage(sb.toString(), email);
    }
    private void sendMessage(String secret, String email) {
        System.out.println("localhost:8080/confirm?secret=" + secret +" как бы отправили сообщение на " + email);
    }

    public long validateConfirm(String secret) throws ValidationException{
        Confirm confirm = confirmationRepository.UserBySecret(secret);
        if (confirm != null){
            return confirm.getUserId();
        } else throw new ValidationException("Your email isn't confirmed");
    }
}