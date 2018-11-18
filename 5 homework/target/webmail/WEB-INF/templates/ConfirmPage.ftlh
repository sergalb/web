<#-- @ftlvariable name="message" type="java.lang.String" -->

<#import "common.ftl" as c>

<@c.page>
    <#if message??>
    <p>${message}</p>
    </#if>

<text>Your email isn't confirmed</text>
</@c.page>