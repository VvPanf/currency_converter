<#macro login path isRegisterForm>
    <div class="row d-flex justify-content-center">
        <form action="/login" method="post" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <div class="card px-5 py-5">
                <h1 class="text-center">
                    <#if isRegisterForm>
                        Регистрация пользователя
                    <#else>
                        Вход в систему
                    </#if>
                </h1>
                <div class="form-group row my-3">
                    <label class="col-sm-4 col-form-label" for="username">Имя: </label>
                    <div class="col-sm-8">
                        <input type="text" id="username" placeholder="Введите имя пользователя" name="username" class="form-control">
                    </div>
                </div>
                <div class="form-group row my-3">
                    <label class="col-sm-4 col-form-label" for="password">Пароль: </label>
                    <div class="col-sm-8">
                        <input type="password" id="password" placeholder="Введите пароль" name="password" class="form-control">
                    </div>
                </div>
<#--            <div th:if="${param.error}">-->
<#--                <div class="alert alert-danger">Неверное имя пользователя или пароль</div>-->
<#--            </div>-->
<#--            <#if isRegisterForm>-->
<#--                <div th:if="${userError}">-->
<#--                    <div class="alert alert-danger">Пользователь с таким именем уже существует</div>-->
<#--                </div>-->
<#--            </#if>-->
                <div class="form-group row my-3">
                    <div class="col text-center">
                        <button type="submit" class="btn btn-dark">
                            <#if isRegisterForm>
                                Зарегистрироваться
                            <#else>
                                Войти
                            </#if>
                        </button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</#macro>
