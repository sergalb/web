package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.model.service.UserService;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RegisterPage extends Page {
    private UserService userService = new UserService();

    private void register(HttpServletRequest request, Map<String, Object> view) {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setUserId(userService.findCount());
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String passwordConfirmation = request.getParameter("passwordConfirmation");

        try {
            userService.validateRegistration(user, password, passwordConfirmation, email);
        } catch (ValidationException e) {
            view.put("login", user.getLogin());
            view.put("email", email);
            view.put("password", password);
            view.put("error", e.getMessage());
            return;
        }
        user.setEmail(email);
        userService.register(user, password);
        throw new RedirectException("/index", "registrationDone");
    }

    private void action() {
        // No operations.
    }
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);

    }
    protected void after(HttpServletRequest request, Map<String, Object> view){
        super.after(request, view);
    }
}
