package ru.itmo.webmail.model.repository.impl;

import ru.itmo.webmail.model.domain.Article;
import ru.itmo.webmail.model.domain.TableObject;
import ru.itmo.webmail.model.exception.RepositoryException;
import ru.itmo.webmail.model.repository.ArticleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepositoryImpl extends CommonRepositoryImpl implements ArticleRepository {
    @Override
    public TableObject toTableObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        return toArticle(metaData, resultSet);
    }
    private Article toArticle(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName = metaData.getColumnName(i);
            if ("id".equalsIgnoreCase(columnName)) {
                article.setId(resultSet.getLong(i));
            }else if ("userId".equalsIgnoreCase(columnName)) {
                article.setUserId(resultSet.getLong(i));
            } else if ("title".equalsIgnoreCase(columnName)) {
                article.setTitle(resultSet.getString(i));
            } else if ("text".equalsIgnoreCase(columnName)) {
                article.setText(resultSet.getString(i));
            } else if ("hidden".equalsIgnoreCase(columnName)) {
                article.setHidden(resultSet.getBoolean(i));
            } else if ("creationTime".equalsIgnoreCase(columnName)) {
                article.setCreationTime(resultSet.getTimestamp(i));
            } else {
                throw new RepositoryException("Unexpected column 'Article." + columnName + "'");
            }
        }
        return article;
    }

    @Override
    public void updateHidden(long id, boolean hidden) {
        super.update("UPDATE Article SET hidden=? WHERE id=?",
                "Article", new Object[]{!hidden, id}, "Can't hide/show Article with id = " + id);
    }

    @Override
    public List<Article> findAllByUser(long userId) {
        List<Article> articles = new ArrayList<>();
        List<TableObject> convert = super.findAll("SELECT * FROM Article WHERE userId=? ORDER BY id",
                "Article", new Object[] {userId});
        for (TableObject aConvert : convert) {
            articles.add((Article) aConvert);
        }
        return articles;
    }

    @Override
    public List<Article> findArticles() {
        List<Article> articles = new ArrayList<>();
        List<TableObject> convert = super.findAll("SELECT * FROM Article ORDER BY id DESC", "Article", new Object[] {});
        for (TableObject aConvert : convert) {
            articles.add((Article) aConvert);
        }
        return articles;
    }

    @Override
    public void sendArticle(long userId, String title, String text) {
        Article article = new Article(userId, title,text);
        super.insert("INSERT INTO Article (userId, title, text, creationTime) VALUES (?, ?, ?, NOW())", "Article", article,
                new Object[]{userId, title, text});
    }
}
