<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page true>
    <#if captchaError??>
    <div class="alert alert-danger" role="alert">
        ${captchaError}
    </div>
    </#if>
    ${message?ifExists}
    <@l.login "/registration" true />
</@c.page>