package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

abstract class Page
{
    private UserService userService = new UserService();
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        view.put("userCount", userService.findCount());
    }
    protected void after(HttpServletRequest request, Map<String, Object> view) {
        view.put("userCount", userService.findCount());
    }
}
