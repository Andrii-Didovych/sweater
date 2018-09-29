<#import "parts/common.ftl" as c>
<@c.page true>
<link rel="stylesheet" href="/static/style.css">
<form action="/problem" method="post">
    <div class="login-registration-style">
        <div class="row justify-content-end pr-3 mb-3">
            <h5><b>We will try to solve your problem!</b></h5>
        </div>
        <div class="form-group row">
            <label class="col-sm-4 col-form-label">User name:</label>
            <div class="col-sm-8">
                <input type="text" name="username" value="<#if username??>${username}</#if>"
                       class="form-control ${(message??)?string('is-invalid', '')}"
                       placeholder="Input username" />
            <#if message??>
                <div class="invalid-feedback">
                    ${message}
                </div>
            </#if>
            </div>
        </div>
        <input class="btn btn-primary" type="hidden" name="_csrf" value="${_csrf.token}" />
        <div class="row justify-content-end pr-3">
            <button class="btn btn-primary" type="submit">Check</button>
        </div>
    </div>
</form>
</@c.page>