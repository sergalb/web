<#-- @ftlvariable name="message" type="java.lang.String" -->

<#import "common.ftl" as c>

<@c.page>
    <#if message??>
    <p>${message}</p>
    </#if>

<text>Wrong confirmation link</text>
</@c.page>