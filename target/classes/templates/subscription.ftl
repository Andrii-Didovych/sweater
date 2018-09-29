
<#import "parts/common.ftl" as c>

<@c.page true>
<link rel="stylesheet" href="/static/style.css">
<div class="login-registration-style">
    <div class="row justify-content-end pr-3 mb-3">
        <h5><b><div>Here are your ${type}</div></b></h5>
    </div>
<div class="row m-0 p-0">
<div class="card-columns p-0"  >
    <#list users as user>
        <a href="/user-messages/${user.id}">
        <div class="card my-3">

        <img src="<#if user.photo??>/img/${user.photo}<#else>http://pngc.mypng.cn/1237/personal.png</#if>" class="card-img-top">
            <div class="card-footer text-muted">
                ${user.getUsername()}
            </div>
        </div>
        </a>
    </#list>
</div>
</div>
    <#list users as user>
    <#else>
    <div class="row justify-content-center mt-3">
        <h6>You do not have any ${type} yet.</h6>
    </div>
    </#list>
</@c.page>