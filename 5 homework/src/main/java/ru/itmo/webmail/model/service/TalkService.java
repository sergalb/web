package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.exception.MessageException;
import ru.itmo.webmail.model.repository.TalkRepository;
import ru.itmo.webmail.model.repository.impl.TalkRepositoryImpl;

import java.util.List;

public class TalkService {
    private TalkRepository talkRepository = new TalkRepositoryImpl();
    public List<Talk> findAllTalks(long userId){
        return talkRepository.findAllTalksByUser(userId);
    }

    public void sendMessage(long sourceId, long targetId, String text){
        talkRepository.setMessage(sourceId, targetId, text);
    }

    public void validateSendMessage(String loginDistanation, String text) throws MessageException{
        if (loginDistanation == null) {
            throw new MessageException("login is required");
        }

        if (text == null || text.isEmpty()) {
            throw new MessageException("message text is empty");
        }
    }
}
