<#import "parts/common.ftl" as c>

<@c.page false>
<link rel="stylesheet" href="/static/greeting.css"/>
    <div class="row greeting-container">
        <div class="col-6 left">
            <div class="row justify-content-center">
            <ul>
                <li><b>Follow your interests.</b></li>
                <li><b>Hear what people are talking about.</b></li>
                <li><b>Join the conversation.</b></li>
            </ul>
            </div>
        </div>
        <div class="col-6 right">
            <div class="right-inside">
                <div class="row mb-4 justify-content-center" style="font-size: 26px;">
                        <b>See what’s happening in the world right now</b>
                </div>
                <b>Join Sweater today.</b>
                <div class="row   justify-content-center">
                    <a class="my-buttons" href="/login">Login</a>
                </div>
                <div class="row white justify-content-center">
                    <a class="my-buttons " href="/registration">Registration</a>
                </div>
            </div>
        </div>
    </div>
</@c.page>
