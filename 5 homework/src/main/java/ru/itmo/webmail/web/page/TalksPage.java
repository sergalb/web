package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.MessageException;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TalksPage extends Page {
    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (getUser() == null) {
            throw new RedirectException("/index");
        }
    }

    public void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("talks", MakeCorrectTalks(getTalkService().findAllTalks(getUser().getId())));
    }

    private void talks(HttpServletRequest request, Map<String, Object> view) {
        String loginDestination = request.getParameter("login");
        String text = request.getParameter("text");
        try {
            getTalkService().validateSendMessage(loginDestination, text);
            User user = getUserService().findId(loginDestination);
            if (user == null) {
                throw new MessageException("User " + loginDestination + " doesn't exist");
            }
        }catch (MessageException e) {
            view.put("error", e.getMessage());
            view.put("talks", MakeCorrectTalks(getTalkService().findAllTalks(getUser().getId())));
            return;
        }
        getTalkService().sendMessage(getUser().getId(), getUserService().findId(loginDestination).getId(), text);
        throw new RedirectException("/talks", "sendMessageDone");
    }

    public void sendMessageDone(HttpServletRequest request, Map<String, Object> view) {
        view.put("talks", MakeCorrectTalks(getTalkService().findAllTalks(getUser().getId())));
        throw new RedirectException("/talks");

    }
    private List<Talk> MakeCorrectTalks(List<Talk> talks) {
        for (Talk talk : talks) {
            talk.setSourceUserLogin(getUserService().find(talk.getSourceUserId()).getLogin());
            talk.setTargetUserLogin(getUserService().find(talk.getTargetUserId()).getLogin());
        }
        Collections.reverse(talks);
        return talks;
    }
}
