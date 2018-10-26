<#include "security.ftl">
<#import "login.ftl" as l>
<style>
    .nav-link {
        padding: 0;
        margin: auto 5px;
    }
</style>

<nav style="border-bottom: 1px solid grey; height: 43px; display: flex; flex-direction: row; background-color: #ffffff;">
        <#--<a class="navbar-brand" href="/">Sweater</a>-->
            <div class="row" style="width: 850px; margin: auto">
                <div class="mr-auto ml-0" style="display: flex; flex-direction: row;">
                        <a class="nav-link" href="/">Home</a>
                    <#if user??>
                    <#if isActive>
                        <a class="nav-link" href="/user-messages/${currentUserId}">My messages</a>

                        <a class="nav-link" href="/user">User list</a>
                        <a class="nav-link" href="/profile">Profile</a>
                    </#if>
                    </#if>
                </div>
                <div class="navbar-text"><#if user?? && isActive>${name}<#else>Please, login</#if></div>
                <@l.logout/>
            </div>
</nav>