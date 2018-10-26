<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">
<@c.page true>
<link rel="stylesheet" href="/static/style.css"/>
<div class="login-registration-style">
    <div class="row justify-content-end pr-3 mb-3">
        <h5><b><div>List of user</div></b></h5>
    </div>
    <div class="card-columns p-0" id="user-id">
    <#list users as user>
        <#if !isAdmin><a href="/user-messages/${user.id}"></#if>
            <div class="card my-3">
                <img src="<#if user.photo??>/img/${user.photo}<#else>http://pngc.mypng.cn/1237/personal.png</#if>" class="card-img-top"/>
                <#if isAdmin>
                <div class="m-2">
                    <a href="/user-messages/${user.id}">${user.getUsername()}</a>
                    <a href="/user/${user.id}">edit</a>
                </div>
                <div class="card-footer text-muted">
                    <#list user.roles as role>${role}<#sep>, </#list>
                </div>
                <#else>
                    <div class="card-footer text-muted">
                        ${user.getUsername()}
                    </div>
                </#if>
            </div>
        <#if !isAdmin></a></#if>
    </#list>
    </div>
</div>
</@c.page>