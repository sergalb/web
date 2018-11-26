<#import "common.ftl" as c>

<@c.page>
<div class="enter form-box">
    <div class="header">Enter</div>
    <div class="body">
        <form method="post" action="/enter">
            <input type="hidden" name="action" value="enter">
            <div class="field">
                <div class="name">
                    <label for="loginOrEmail">Login/Email</label>
                </div>
                <div class="value">
                    <input id="loginOrEmail" name="loginOrEmail" value="${loginOrEmail!}"/>
                </div>
            </div>
            <div class="field">
                <div class="name">
                    <label for="password">Password</label>
                </div>
                <div class="value">
                    <input id="password" type="password" name="password" value="${password!}"/>
                </div>
            </div>
            <div class="button-field">
                <input type="submit" value="Enter">
            </div>
            <#if error??>
            <div class="error">
                ${error}
            </div>
            </#if>
        </form>
    </div>
</div>
<script>
    $(function() {
        $(".enter form").submit(function () {
            var loginOrEmail = $(this).find("input[name='loginOrEmail']").val();
            var password = $(this).find("input[name='password']").val();
            var $error = $(this).find(".error").text("");
            $.post("", {action: "enter", loginOrEmail: loginOrEmail, password: password}, function (response) {
                if (response["success"]) {
                    setMessage("Hello, " + loginOrEmail);
                    document.location.href = "/";
                } else {
                    $error.text(response["error"]);
                }
            });
            return false;
        });
    })
</script>
</@c.page>
