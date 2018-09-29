
<div class="collapse <#if message?? || textError??>show</#if>"  id="collapseExample">

    <div class="form-group">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" <#if message??>value="${message.text}"</#if>  placeholder="Type a message"  name="text" class="form-control ${(textError??)?string('is-invalid', '')}">
                <div class="invalid-feedback">
                    <#if textError??>
                        ${textError}
                    </#if>
                </div>
            </div>

            <div class="form-group">
                <input type="text" <#if message??>value="${message.tag}"</#if>  placeholder="Type a tag"  name="tag" class="form-control ${(tagError??)?string('is-invalid', '')}">
                <div class="invalid-feedback">
                    <#if tagError??>
                        ${tagError}
                    </#if>
                </div>
            </div>

            <div class="form-group">
                <div class="custom-file">
                    <input type="file" name="file" id="customFile">
                    <label class="custom-file-label" for="customFile">Choose file</label>
                </div>
            </div>
            <#if message??>
            <input type="hidden" name="id" value="${message.id}" />
            </#if>
            <div class="form-group row justify-content-end pr-3">
                <#if message??>
                <form method="post" action="/delete">
                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    <button formaction="/delete" class="btn btn-sm btn-danger mr-2" type="submit">Delete</button>
                </form>
                </#if>
                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                <button type="submit" class="btn btn-sm btn-primary">Publish</button>
            </div>
        </form>
    </div>
</div>