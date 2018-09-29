<#import "parts/common.ftl" as c>

<@c.page true>
<link rel="stylesheet" href="/static/style.css"/>
    <#if messageError??>
    <div class="alert alert-success" role="alert">
        ${messageError}
    </div>
    </#if>
<form action="/profile" method="post" enctype="multipart/form-data">
    <div class="login-registration-style">
        <div class="row justify-content-end pr-3 mb-3">
            <h5><b>Here you can change an email</b></h5>
        </div>
        <div class="form-group row">
            <label class="col-sm-4 col-form-label">Password:</label>
            <div class="col-sm-8">
                <input   name="password" type="password" autocomplete="new-password" class="form-control ${(passwordError??)?string('is-invalid', '')}" placeholder="Enter password" />
                    <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                 </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-4 col-form-label">Email:</label>
            <div class="col-sm-8">
                <input name="email" type="email"  class="form-control ${(emailError??)?string('is-invalid', '')}" placeholder="Email"/>
                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
            </div>
        </div>
        <div class="row justify-content-end pr-3">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button class="btn btn-primary" type="submit">Update</button>
        </div>
    </div>

    <div class="login-registration-style">
        <div class="row justify-content-end pr-3 mb-3">
            <h5><b>You also might change password</b></h5>
        </div>
        <div class="form-group row">
            <label class="col-sm-4 col-form-label">Old password:</label>
            <div class="col-sm-8">
                <input   name="password" type="password" autocomplete="new-password" class="form-control ${(oldPasswordError??)?string('is-invalid', '')}" placeholder="Enter old password" />
                    <#if oldPasswordError??>
                    <div class="invalid-feedback">
                        ${oldPasswordError}
                    </div>
                    </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-4 col-form-label">New password:</label>
            <div class="col-sm-8">
                <input name="newPassword" type="password" autocomplete="new-password" class="form-control ${(newPasswordError??)?string('is-invalid', '')}" placeholder="Enter new password" />
                    <#if newPasswordError??>
                    <div class="invalid-feedback">
                        ${newPasswordError}
                    </div>
                    </#if>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-4 col-form-label">Repeat password:</label>
            <div class="col-sm-8">
                <input   name="repeatPassword" type="password" autocomplete="new-password" class="form-control ${(repeatPasswordError??)?string('is-invalid', '')}" placeholder="Repeat new password" />
                    <#if repeatPasswordError??>
                    <div class="invalid-feedback">
                        ${repeatPasswordError}
                    </div>
                    </#if>
            </div>
        </div>
        <div class="row justify-content-end pr-3">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <button formaction="/profile/password" class="btn btn-primary" type="submit">Update</button>
        </div>
    </div>

    <div class="login-registration-style">
        <div class="row justify-content-end pr-3 mb-3">
            <h5><b>Add a photo for your account</b></h5>
        </div>
        <div class="form-group row">
            <div class="ml-3 mb-2 custom-file  col-sm-9 mr-3">
                <input type="file" name="photoOfUser" id="customFile">
                <label class="custom-file-label form-control ${(fileError??)?string('is-invalid', '')}" for="customFile">Choose file</label>
                <#if fileError??>
                    <div class="invalid-feedback">
                        ${fileError}
                    </div>
                </#if>
            </div>
            <div class="col-sm-1">
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <button formaction="/profile/photo" class="btn btn-primary" type="submit">Upload</button>
            </div>
        </div>
        <#if photo??>
                    <img src="/img/${photo}" class="card-img-top">
        </#if>
    </div>
</form>
</@c.page>