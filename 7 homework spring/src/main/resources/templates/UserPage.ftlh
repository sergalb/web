<#import "./common.ftlh" as c>

<@c.page>
<div class="datatable">
    <div class="caption">User</div>
    <#if user??>
        <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Login</th>
            <th>Created At</th>
        </tr>
        </thead>
        <tbody>
            <tr>
                <td>${user.id!}</td>
                <td>${user.login!}</td>
                <td>${user.creationTime!}</td>
                </tr>
            </tbody>
        </table>
    <#else>
        <p>No such user</p>
    </#if>
</div>
</@c.page>
