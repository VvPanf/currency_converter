<#import "parts/common.ftl" as c>
<@c.page>
    <div class="card px-5 py-5">
        <h1 class="text-center">Курс валют на сегодня</h1>
        <table class="table">
            <tr>
                <th scope="col">No</th>
                <th scope="col">Числовой код</th>
                <th scope="col">Символьный код</th>
                <th scope="col">Название</th>
                <th scope="col">Номинал</th>
                <th scope="col">Значение</th>
                <th scope="col">Дата</th>
            </tr>
            <#list currRates as c>
                <tr>
                    <td scope="row">${c?counter}</td>
                    <td>${c.currency.numCode}</td>
                    <td>${c.currency.chrCode}</td>
                    <td>${c.currency.currName}</td>
                    <td>${c.currency.nominal}</td>
                    <td>${c.value}</td>
                    <td>${c.date}</td>
                </tr>
            </#list>
        </table>
    </div>
</@c.page>