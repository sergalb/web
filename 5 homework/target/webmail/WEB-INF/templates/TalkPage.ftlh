<#-- @ftlvariable name="talks" type="ru.itmo.webmail.model.domain.Talk[]" -->
<#import "common.ftl" as c>

<@c.page>
<div class="form-box">
    <div class="header">Send message</div>
    <div class="body">
        <form method="post" action="/talk">
            <input type="hidden" name="action" value="enter">
            <div class="field">
                <div class="name">
                    <label for="login">Login to whom</label>
                </div>
                <div class="value">
                    <input id="login" name="login" value="${loginDistanation!}"/>
                </div>
            </div>
            <div class="field">
                <div class="name">
                    <label for="text">Text</label>
                </div>
                <div class="value">
                    <input id="text" type="text" name="text" value="${text!}"/>
                </div>
            </div>
            <div class="button-field">
                <input type="submit" value="Send">
            </div>
            <#if error??>
                <div class="error">
                ${error}
                </div>
            </#if>
        </form>
    </div>
</div>

<div class="datatable">
    <div class="caption">Talk</div>
    <table>
        <thead>
        <tr>
            <th>source user</th>
            <th>target user</th>
            <th>Text</th>
        </tr>
        </thead>
        <tbody>
            <#list talks as talk>
            <tr>
                <td>
                ${talk.sourceUserLogin}
                </td>
                <td>
                ${talk.targetUserLogin}
                </td>
                <td>
                ${talk.text}
                </td>
            </tr>
            </#list>
    </table>
</div>
</@c.page>