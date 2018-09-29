<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<#include "parts/security.ftl">

<@c.page true>
     <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
    <div class="mb-0 alert alert-danger" role="alert">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        There is some problem <a href="/problem">Info</a>
    </div>
     </#if>

    <#if message??>
        <div class="mt-0 alert alert-${messageType}" role="alert">
            ${message}
        </div>
    </#if>
    <@l.login "/login" false/>
</@c.page>