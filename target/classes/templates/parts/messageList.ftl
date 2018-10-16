<#include "security.ftl">
<#import "pager.ftl" as p>

    <#list page.content as message>
        <div class="card mb-2 px-1 pb-1">
            <div class="m-2 row m-0 text-muted pr-0">
                <div class="col-sm-3 row p-0">
                    <a href="/user-messages/${message.author.id}">
                        <img src="<#if message.author.photo??>/img/${message.author.photo}<#else>http://pngc.mypng.cn/1237/personal.png</#if>" alt="..." class="rounded mx-2" style="width: 30px; height: 30px;">
                        ${message.authorName}
                    </a>
                </div>
                <div class="col-sm-9 p-0 " style="text-align: right">
                     <#if message.author.id == currentUserId>
                         <a class=" m-0 " href="/user-messages/${message.author.id}?message=${message.id}">Edit</a>
                     </#if>
                </div>
            </div>
            <div class="m-2">
                <span>${message.text}</span><br/>
                <i style="font-size: 15px; margin-left: 5px;"><#if message.tag!=''>#${message.tag}</#if></i>
            </div>
            <#if message.filename??>
                <img src="/img/${message.filename}" class="card-img-top">
            </#if>
        </div>
    <#else>
        <div class="row justify-content-center card-footer row m-0 ml-3 text-muted" style="width: 93%; text-align: center">
            <h6>Here is not any messages yet</h6>
        </div>
    </#list>

    <@p.pager url page/>

