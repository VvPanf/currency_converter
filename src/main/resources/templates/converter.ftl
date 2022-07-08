<#import "parts/common.ftl" as c>
<@c.page>
    <div class="card px-5 py-5">
        <form method="post" action="/converter" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <h1 class="text-center">Конвертация</h1>
            <div class="container">
                <div class="row my-3">
                    <div class="col">
                        <select name="amountCurrId" class="form-control">
                            <#list currSelect as c1>
                                <#if conv.amountCurrId?? && c1.id == conv.amountCurrId>
                                    <option value="${c1.id}" selected>${c1.chrCode} (${c1.currName})</option>
                                <#else>
                                    <option value="${c1.id}">${c1.chrCode} (${c1.currName})</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                    <div class="col">
                        <select name="resultCurrId" class="form-control">
                            <#list currSelect as c2>
                                <#if conv.resultCurrId?? && c2.id == conv.resultCurrId>
                                    <option value="${c2.id}" selected>${c2.chrCode} (${c2.currName})</option>
                                <#else>
                                    <option value="${c2.id}">${c2.chrCode} (${c2.currName})</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="row my-3">
                    <div class="col">
                        <input type="text" pattern="\d+(\.\d{2})?" placeholder="Enter value" name="amount" value="${conv.amount!''}" class="form-control">
                    </div>
                    <div class="col">
                        <output name="resultCurrId" class="form-control">${(conv.result!'')?replace(',','.')}</output>
                    </div>
                </div>
                <div class="row my-3">
                    <div class="col text-center">
                        <button type="submit" class="btn btn-dark">Конвертировать</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</@c.page>