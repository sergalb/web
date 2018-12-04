package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.domain.User;
import ru.itmo.webmail.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class MyArticlesPage extends Page {
    @Override
    public void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (getUser() == null) {
            throw new RedirectException("/index");
        }
    }

    private Map<String, Object> changeShow(HttpServletRequest request, Map<String, Object> view) {
        getArticleService().changeShow(Long.parseLong(request.getParameter("id")),
        Boolean.parseBoolean(request.getParameter("hidden")));
        view.put("success", true);
        return view;
    }

    private List<Article> find(HttpServletRequest request, Map<String, Object> view) {
        return getArticleService().findAllByUser(getUser().getId());
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}
