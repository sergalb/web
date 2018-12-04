<#-- @ftlvariable name="message" type="java.lang.String" -->

<#import "common.ftl" as c>

<@c.page>
    <#if message??>
        <p>${message}</p>
    </#if>
<div class="placeForArticle">
</div>
<template class = "articleTemplate">
    <article>
        <div class="title">{{title}}</div>
        <div class="information">{{login}}</div>
        <div class="body">{{text}}</div>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                <div class="time">{{time}}</div>
            </div>
        </div>
    </article>
</template>
<script>
    $(function () {
        $.post("", {action: "findArticles"}, function (articles) {
            var $body = $(".placeForArticle");
            if (articles.length > 0) {
                articles.forEach(function (article) {
                    if (!article.hidden) {
                        var $art = cloneTemplate($("template.articleTemplate"));
                        $art.find(".title").text(article.title);
                        $art.find(".body").text(article.text);
                        $art.find(".information").text("By " + article.login);
                        $art.find(".time").text(article.creationTime);
                        $body.append($art);
                    }
                });
            }
        })
    })
</script>
</@c.page>