package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.model.exception.MessageException;
import ru.itmo.webmail.model.exception.ValidationException;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage extends Page{
    private Map<String, Object> article(HttpServletRequest request, Map<String, Object> view) {
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        getArticleService().createArticle(getUser().getId(), title, text);
        view.put("success", true);
        return view;
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}
