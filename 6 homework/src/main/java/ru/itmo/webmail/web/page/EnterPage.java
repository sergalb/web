package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class EnterPage extends Page {
    private Map<String, Object> enter(HttpServletRequest request, Map<String, Object> view) {
        String loginOrEmail = request.getParameter("loginOrEmail");
        String password = request.getParameter("password");

        try {
            getUserService().validateEnter(loginOrEmail, password);
        } catch (ValidationException e) {
            view.put("success", false);
            view.put("error", e.getMessage());
            return view;
        }

        login(request, getUserService().authenticate(loginOrEmail, password));
        view.put("success", true);
        return view;
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}
