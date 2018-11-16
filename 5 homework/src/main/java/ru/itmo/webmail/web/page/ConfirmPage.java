package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ConfirmPage extends Page {
    void confirm(HttpServletRequest request, Map<String, Object> view) {
        String secret = request.getParameter("secret");
        /*try {
            getConfirmationService().validateConfirm(secret);
        } catch (ValidationException e) {
            return;
        }*/
    }
}
