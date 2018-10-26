<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">
<@c.page true>
<link rel="stylesheet" href="/static/style.css"/>
<div class="login-registration-style">
    <div class="row justify-content-end pr-3 mb-3">
        <h5><b><div>Edit information about user</div></b></h5>
    </div>
    <form action="/user" method="post">
        <input type="text" class="form-control" <#if currentUser.id!=1>readonly</#if> name="username" value="${currentUser.username}">
        <#list roles as role>
        <div>
            <label><input  type="checkbox"  <#if currentUser.id==1 && role=='ADMIN'>checked disabled</#if> name="${role}" ${currentUser.roles?seq_contains(role)?string("checked", "")}>${role}</label>
        </div>
        </#list>
        <input type="hidden" value="${currentUser.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit">Save</button>
    </form>
</div>
</@c.page>