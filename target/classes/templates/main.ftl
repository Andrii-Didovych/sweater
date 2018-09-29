<#import "parts/common.ftl" as c>
<@c.page true>
<div class="row" style="max-width: 850px; margin: 10px auto;">
    <div class="col-sm-3 pl-0 pr-2">
        <div class="card">
             <#if photo??>
                <img src="/img/${photo}" class="card-img-top">
             </#if>
            <div class="card-footer text-muted">
                ${authenticatedUser.username}</br>
                <#if authenticatedUser.email??> ${authenticatedUser.email}<#else>Email missed</#if>
            </div>
            <div class="m-2">
                <ul>
                    <li><a href="/subscriptions/${authenticatedUser.id}/list">Subscriptions: ${subscriptionsCount}</a></li>
                    <li><a href="/subscribers/${authenticatedUser.id}/list">Subscribers: ${subscribersCount}</a></li>
                    <li><a href="/user-messages/${authenticatedUser.id}">Count of twit: ${messagesCount}</li>
                </ul>
            </div>
        </div>
    </div>
    <div class="col-sm-6 px-0" >
        <form method="get" action="/" class="form-inline mb-2">
            <div class="col-sm-3 p-0">
                <a class="btn   btn-sm  btn-success" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
                    Add message
                </a>
            </div>
            <div class="col-sm-9 p-0" style="text-align: right">
                <input type="text" name="filter" class="form-control form-control-sm" value="${filter?ifExists}" placeholder="Search by tag">
                <button type="submit" class="btn btn-primary  btn-sm">Search</button>
            </div>
        </form>

            <#include "parts/messageEdit.ftl"/>

            <#include "parts/messageList.ftl"/>

    </div>
    <div class="col-sm-3 pl-2 pr-0">
            <#include "parts/blockForFiveUser.ftl"/>
    </div>
</div>
</@c.page>