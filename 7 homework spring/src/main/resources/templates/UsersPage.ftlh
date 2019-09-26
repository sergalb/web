<#import "./common.ftlh" as c>

<@c.page>
<div class="datatable">
    <div class="caption">Users</div>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Login</th>
            <th>Created At</th>
        </tr>
        </thead>
        <tbody>
    <#list users as user>
    <tr>
        <td>${user.id}</td>
        <td>${user.login}</td>
        <td>${user.creationTime}</td>
        <td>
            <form method="post" action="/change">
                <div class="button-field">
                    <#if user.disabled>
                        <input type="submit" value="Enable">
                    <#else>
                        <input type="submit" value="Disable">
                    </#if>
                </div>
                <input type="hidden" name="userId" value=${user.id}>
            </form>
        </td>
    </tr>
    </#list>
        </tbody>
    </table>
</div>
</@c.page>
