package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ConfirmPage extends Page {
    void action(HttpServletRequest request, Map<String, Object> view) {
        String secret = request.getParameter("secret");
        long userId;
        try {
            userId = getConfirmationService().validateConfirm(secret);
        } catch (ValidationException e) {
            view.put("error", e.getMessage());
            return;
        }
        getUserService().confirmedEmail(userId);
        throw new RedirectException("/index", "confirmationDone");
    }
}