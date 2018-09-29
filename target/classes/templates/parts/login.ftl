<#macro login path isRegisterForm>
<link rel="stylesheet" href="/static/style.css">
<form action="${path}" method="post">

    <div class="login-registration-style">
    <div class="offset-sm-5 mb-4">
        <h4><b><#if isRegisterForm>Create new account<#else>Log in to Sweater</#if></b></h4>
    </div>
    <div class="form-group row">
        <label class="col-sm-4 col-form-label">User Name :</label>
        <div class="col-sm-8">
            <input type="text" name="username"
                   class="form-control ${(usernameError??)?string('is-invalid', '')}"
                   placeholder="User name" />
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-4 col-form-label">Password:</label>
        <div class="col-sm-8">
            <input type="password" name="password"
                   class="form-control ${(passwordError??)?string('is-invalid', '')}"
                   placeholder="Password" />
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
        </div>
    </div>

    <#if isRegisterForm>
        <div class="form-group row">
            <label class="col-sm-4 col-form-label">Password:</label>
            <div class="col-sm-8">
                <input type="password" name="password2"
                       class="form-control ${(password2Error??)?string('is-invalid', '')}"
                       placeholder="Retype password" />
                <#if password2Error??>
                    <div class="invalid-feedback">
                        ${password2Error}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-4 col-form-label">Email:</label>
            <div class="col-sm-8">
                <input  name="email" value="<#if user??>${user.email}</#if>"
                       class="form-control ${(emailError??)?string('is-invalid', '')}"
                       placeholder="some@some.com" />
                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
            </div>
        </div>
    </#if>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button class="btn btn-primary " type="submit" style="float: right"><#if isRegisterForm>Create<#else>Sign In</#if></button>
        <div class="col-sm-4">
            <#if !isRegisterForm><a href="/registration">Add new user</a></#if>
        </div>
    </div>
</form>
</#macro>

<#macro logout>
    <#include "security.ftl">
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button class="btn btn-primary btn-sm" style="margin: 5px" type="submit"><#if user?? &&isActive>Sign Out<#else>Log in</#if></button>
</form>
</#macro>