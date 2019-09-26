package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wm4.domain.User;
import ru.itmo.wm4.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/users")
    public String main(Model model, HttpSession httpSession) {
        if (getUser(httpSession) == null) {
            return "redirect:/";
        }
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }
    @PostMapping("/change")
    public String changeDisabled(HttpServletRequest request){
        User cur = getUser(request.getSession());
        if (cur == null) {
            return "redirect:/";
        }
        long userId = Long.parseLong(request.getParameter("userId"));
        User user = userService.findById(userId);
        if (user != null) {
            user.setDisabled(!user.isDisabled());
            userService.updateDisabled(userId, user.isDisabled());
            if (cur.getId() == userId && user.isDisabled()) {
                unsetUser(request.getSession());
            }
        }
        return "redirect:/users";
    }
}
