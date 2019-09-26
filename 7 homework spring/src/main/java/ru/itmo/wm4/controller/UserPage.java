package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wm4.domain.User;
import ru.itmo.wm4.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/user/{userId}")
    public String main(@PathVariable String userId, Model model) {
        User user = userService.findById(Long.parseLong(userId));
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "UserPage";
    }
}