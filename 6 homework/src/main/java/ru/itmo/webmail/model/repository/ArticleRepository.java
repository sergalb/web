package ru.itmo.webmail.model.repository;

import ru.itmo.webmail.model.domain.Article;

import java.util.List;

public interface ArticleRepository extends CommonRepository {
    void sendArticle(long id, String title, String text);

    List<Article> findArticles();

    List<Article> findAllByUser(long userId);

    void updateHidden(long id, boolean hidden);
}
