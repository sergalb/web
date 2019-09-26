<#import "/spring.ftl" as spring>
<#import "./common.ftlh" as c>

<#macro error field>
    <@spring.bind field/>
    <#list spring.status.errorMessages as error>
        <div class="error">${error}</div>
        <#break/>
    </#list>
</#macro>

<@c.page>
    <div class="addNotice form-box">
        <div class="header">Add Notice</div>
        <div class="body">
            <form method="post" action="/addNotice">
                <div class="field">
                    <div class="name">
                        <label for="content">Content</label>
                    </div>
                    <div class="value">
                        <textarea id="content" name="content">${noticeForm.content!}</textarea>
                    </div>
                    <@error 'noticeForm.content'/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Add notice">
                </div>
            </form>
        </div>
    </div>
</@c.page>
