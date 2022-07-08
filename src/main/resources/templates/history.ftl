<#import "parts/common.ftl" as c>
<@c.page>
    <div class="card px-5 py-5">
        <form method="post" action="/history" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <h1 class="text-center">История конвертаций</h1>
            <div class="container">
                <div class="row my-3">
                    <div class="col">
                        <input type="text" pattern="\d\d\d\d-\d\d-\d\d" placeholder="гггг-ММ-дд" name="date" value="${historyFilter.date!''}" class="form-control">
                    </div>
                    <div class="col">
                        <select name="amountCurr" class="form-control">
                            <#list currSelect as c>
                                <#if historyFilter.amountCurr?? && c.id == historyFilter.amountCurr>
                                    <option value="${c.id}" selected>${c.chrCode} (${c.currName})</option>
                                <#else>
                                    <option value="${c.id}">${c.chrCode} (${c.currName})</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                    <div class="col">
                        <select name="resultCurr" class="form-control">
                            <#list currSelect as c>
                                <#if historyFilter.resultCurr?? && c.id == historyFilter.resultCurr>
                                    <option value="${c.id}" selected>${c.chrCode} (${c.currName})</option>
                                <#else>
                                    <option value="${c.id}">${c.chrCode} (${c.currName})</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                </div>
            </div>
            <div class="row my-3">
                <div class="col text-center">
                    <button type="submit" class="btn btn-dark">Найти</button>
                </div>
            </div>
        </form>
    </div>
    <table class="table">
        <tr>
            <th scope="col">No</th>
            <th scope="col">Исходная валюта</th>
            <th scope="col">Целевая валюта</th>
            <th scope="col">Исходная сумма</th>
            <th scope="col">Получаемая сумма</th>
            <th scope="col">Дата</th>
        </tr>
        <#if converts??>
            <#list converts as c>
                <tr>
                    <td scope="row">${c?counter}</td>
                    <td>${c.amountCurr}</td>
                    <td>${c.resultCurr}</td>
                    <td>${c.amount}</td>
                    <td>${c.result}</td>
                    <td>${c.date}</td>
                </tr>
            </#list>
        </#if>
    </table>
</@c.page>