package ru.itmo.webmail.web.page;

import ru.itmo.webmail.model.domain.Article;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class IndexPage extends Page {
    private void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    private void registrationDone(HttpServletRequest request, Map<String, Object> view) {
        view.put("message", "You have been registered");
    }

    private List<Article> findArticles(HttpServletRequest request, Map<String, Object> view) {
        List<Article> articles = getArticleService().findAll();
        for (Article article : articles) {
            article.setLogin(getUserService().find(article.getUserId()).getLogin());
        }
        return articles;
    }
}
