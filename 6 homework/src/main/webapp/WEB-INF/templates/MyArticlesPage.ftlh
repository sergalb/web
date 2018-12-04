<#-- @ftlvariable name="articles" type="ru.itmo.webmail.model.domain.Article[]" -->
<#import "common.ftl" as c>

<@c.page>

<div class="datatable articles">
    <div class="caption">my articles</div>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>title</th>
            <th>change show</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td colspan="3" class="no-data">No data</td>
        </tr>
        </tbody>
    </table>
</div>

<template class="articles-tr">
    <tr>
        <td class="id">{{#}}</td>
        <td class="title">{{title}}</td>
        <td class="change">
            <input type="submit">
        </td>
    </tr>
</template>

<script>
    $(function () {
        var $noData = $(".articles .no-data");
        $noData.text("Loading...");
        $.post("", {action: "find"}, function (articlesList) {
            var $tbody = $(".articles tbody");
            if (articlesList.length > 0) {
                $tbody.empty();
                articlesList.forEach(function (article) {
                    var $tr = cloneTemplate($("template.articles-tr"));
                    $tr.find(".id").text(article.id);
                    $tr.find(".title").text(article.title);
                    if (article.hidden) {
                        $tr.find(".change input").attr("value", "Show");
                    } else {
                        $tr.find(".change input").attr("value", "Hide");
                    }
                    $tbody.append($tr);
                });
            } else {
                $noData.text("No data");
            }
        })
    });

    $(function() {
        $(".datatable.articles").on("click", ".change input", function () {
            var id = $(this).parent().parent().find(".id").html();
            var button = $(this);
            var hidden = this.value === "Show";
            $.post("", {action: "changeShow", id: id, hidden: hidden}, function (response) {
                if (response["success"]) {
                    if (hidden) {
                        button.attr("value", "Hide")
                    } else {
                        button.attr("value", "Show")
                    }
                }
            });
            return false;
        });
    })
</script>
</@c.page>