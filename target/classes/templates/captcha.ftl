<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page true>
<link rel="stylesheet" href="/static/style.css">
<form action="/captcha" method="post">
    <#if captchaError??>
    <div class="alert alert-danger" role="alert">
    ${captchaError}
    </div>
    </#if>
    <div class="login-registration-style">
        <div class="mb-4 col align-self-center">
            <h5><b>We wont to be confident that you are a real user</b></h5>
        </div>
    <div class="mb-4">
        <div class="row justify-content-center g-recaptcha" data-sitekey="6LcxVXEUAAAAAGNhjCLrrC_AHZ4p-KSGyo2pTCrv"></div>
    </div>
    <div class="row justify-content-center">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary" formaction="/captcha/<#if id??>${id}</#if>" type="submit">Confirm</button>
    </div>
    </div>
</form>
</@c.page>