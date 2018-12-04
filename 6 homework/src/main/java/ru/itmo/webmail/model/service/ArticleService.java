package ru.itmo.webmail.model.service;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.repository.ArticleRepository;
import ru.itmo.webmail.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private ArticleRepository articleRepository = new ArticleRepositoryImpl();
    public void createArticle(long userId, String title, String text) {
        articleRepository.sendArticle(userId, title, text);
    }

    public List<Article> findAll() {
        return articleRepository.findArticles();
    }

    public List<Article> findAllByUser(long userId) {
        return articleRepository.findAllByUser(userId);
    }

    public void changeShow(long id, boolean hidden) {
        articleRepository.updateHidden(id, hidden);
    }
}
