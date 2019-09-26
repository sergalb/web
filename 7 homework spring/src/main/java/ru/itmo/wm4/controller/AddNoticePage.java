package ru.itmo.wm4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wm4.form.NoticeCredentials;
import ru.itmo.wm4.service.NoticeService;

import javax.validation.Valid;

@Controller
public class AddNoticePage extends Page{
    @GetMapping(path = "/addNotice")
    public String noticeGet(Model model) {
        model.addAttribute("noticeForm", new NoticeCredentials());
        return "AddNoticePage";
    }

    @PostMapping(path = "/addNotice")
    public String noticePost(@Valid @ModelAttribute("noticeForm") NoticeCredentials noticeForm,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "AddNoticePage";
        }

        getNoticeService().addNotice(noticeForm);

        return "redirect:/";
    }
}
