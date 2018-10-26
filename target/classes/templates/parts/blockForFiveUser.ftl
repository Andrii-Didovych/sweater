<div class="card pb-2 m-0 pt-3">
    <div class="row justify-content row m-0 ml-3 text-muted" style="width: 80%; text-align: center">
        <#if userChannel??><h6 class="ml-3"><a href="/subscriptions/${userChannel.id}/list">Following</a></h6></#if>
        <#if authenticatedUser??><h6 class="ml-3"><a href="/subscriptions/${authenticatedUser.id}/list">Following</a></h6></#if>
    </div>
<#list fiveSubscriptions as subscription>
            <div class="m-1 row m-0 text-muted pr-0">
                    <a href="/user-messages/${subscription.id}">
                        <img src="<#if subscription.photo??>/img/${subscription.photo}<#else>http://pngc.mypng.cn/1237/personal.png</#if>" alt="..." class="rounded mx-2" style="width: 40px; height: 40px;"/>
                        ${subscription.username}
                    </a>
            </div>

<#else>
        <div class="row justify-content-center card-footer row m-0 ml-3 text-muted" style="width: 80%; text-align: center">
            <h6>List is empty</h6>
        </div>
</#list>
</div>

<div class="card mt-2 pb-2 m-0 pt-3 ">
    <div class="row justify-content row m-0 ml-3 text-muted" style="text-align: center">
        <#if userChannel??><h6 class="ml-3"><a href="/subscribers/${userChannel.id}/list">Followers</a></h6></#if>
        <#if authenticatedUser??><h6 class="ml-3"><a href="/subscribers/${authenticatedUser.id}/list">Followers</a></h6></#if>
    </div>
<#list fiveSubscribers as subscription>
            <div class="m-1 row m-0 text-muted pr-0">
                <a href="/user-messages/${subscription.id}">
                    <img src="<#if subscription.photo??>/img/${subscription.photo}<#else>http://pngc.mypng.cn/1237/personal.png</#if>" alt="..." class="rounded mx-2" style="width: 40px; height: 40px;"/>
                    ${subscription.username}
                </a>
            </div>
<#else>
        <div class="row justify-content-center card-footer row m-0 ml-3 text-muted" style="width: 80%; text-align: center">
            <h6>List is empty</h6>
        </div>
</#list>
</div>