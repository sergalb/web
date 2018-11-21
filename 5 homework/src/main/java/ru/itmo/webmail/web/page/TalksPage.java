package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Talk;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.MessageException;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
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
        List<Talk> talks = getTalkService().findAllTalks(getUser().getId());
        for (Talk talk : talks) {
            talk.setSourceUserLogin(getUserService().find(talk.getSourceUserId()).getLogin());
            talk.setTargetUserLogin(getUserService().find(talk.getTargetUserId()).getLogin());
            //System.out.println();
        }
        view.put("talks", talks);
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
        }
        getTalkService().sendMessage(getUser().getId(), getUserService().findId(loginDestination).getId(), text);
        throw new RedirectException("/talks", "sendMessageDone");
    }

    public void sendMessageDone(HttpServletRequest request, Map<String, Object> view) {
        List<Talk> talks = getTalkService().findAllTalks(getUser().getId());
        for (Talk talk : talks) {
            talk.setSourceUserLogin(getUserService().find(talk.getSourceUserId()).getLogin());
            talk.setTargetUserLogin(getUserService().find(talk.getTargetUserId()).getLogin());
        }
        view.put("talks", talks);
    }
}
