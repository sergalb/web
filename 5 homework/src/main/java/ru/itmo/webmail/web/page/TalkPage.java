package ru.itmo.webmail.web.page;

import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TalkPage extends Page {
    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (getUser() == null) {
            throw new RedirectException("/index");
        }
    }

    public void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("talks", getTalkService().findAllTalks(getUser().getId()));
    }

    private void talk(HttpServletRequest request, Map<String, Object> view) {

        throw new RedirectException("/talk");
    }
}
