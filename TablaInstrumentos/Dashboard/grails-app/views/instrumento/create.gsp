<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'instrumento.label', default: 'Instrumento')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#create-instrumento" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="create-instrumento" class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.instrumento}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.instrumento}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.instrumento}" method="POST">

            <fieldset>
              <br/>
            <div style="border-style:solid;border-color:black;border-width:1px;background-color:#E0FFFF;width:350px;text-align:center">
            						<table width="300" >

            							<tr>
            								<td>TiposDeGraficos:</td>
            								<td>
                              <g:select name="TiposDeGraficos" from="${dashboard.ExtretagiasVisualización?.values()}"/>
            								</td>
            							</tr>
                          <tr>
            								<td>TiposDeConteo:</td>
            							</tr>

            							<tr>
            								<td> &nbsp; </td>
            								<td><g:actionSubmit action="agregarSubMeta" value="Crear"/></td>
            							</tr>
            						</table>
            					</div>
                </fieldset>


                <fieldset class="buttons">
                    <g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
