<#import "parts/common.ftl" as c>

<@c.page true>

<div class="row" style="max-width: 850px; margin: 10px auto;">
    <div class="col-sm-3 pr-2 pl-0 m-0">
        <div class="card">
                     <#if userChannel.photo??>
                        <img src="/img/${userChannel.photo}" class="card-img-top">
                     </#if>
            <div class="card-footer text-muted">
                ${userChannel.username}</br>
                        <#if userChannel.email??> ${userChannel.email}<#else>Email missed</#if>
            </div>
            <div class="m-2">
                <ul>
                    <li><a href="/subscriptions/${userChannel.id}/list">Subscriptions: ${subscriptionsCount}</a></li>
                    <li><a href="/subscribers/${userChannel.id}/list">Subscribers: ${subscribersCount}</a></li>
                    <li>Count of twit: ${messagesCount}</li>
                </ul>
                <div class="row justify-content-center  ">
                <#if !isCurrentUser>
                    <#if isSubscriber>
                        <a style="width: 80%;" class="btn btn-info" href="/unsubscribe/${userChannel.id}">Unsubscribe</a>
                    <#else>
                        <a style="width: 80%;" class="btn btn-info" href="/subscribe/${userChannel.id}">Subscribe</a>
                    </#if>
                </#if>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-6 px-0 m-0" >
        <#if isCurrentUser>
            <#include "parts/messageEdit.ftl" />
        </#if>

        <#include "parts/messageList.ftl" />
    </div>
    <div class="col-sm-3 pl-2 pr-0 m-0">
            <#include "parts/blockForFiveUser.ftl"/>
    </div>
</div>
</@c.page>