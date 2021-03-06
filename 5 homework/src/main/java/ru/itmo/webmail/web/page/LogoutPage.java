package ru.itmo.webmail.web.page;

import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LogoutPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        request.getSession().removeAttribute(USER_ID_SESSION_KEY);
        getEventService().doEvent(getUser().getId(), "logout");
        throw new RedirectException("/index");
    }
}
