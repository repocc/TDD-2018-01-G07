<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'motorDeDatos.label', default: 'MotorDeDatos')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#edit-motorDeDatos" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="edit-motorDeDatos" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.motorDeDatos}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.motorDeDatos}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.motorDeDatos}" method="PUT">
                <g:hiddenField name="version" value="${this.motorDeDatos?.version}" />
                <fieldset class="form">
                    <f:all bean="motorDeDatos"/>
                </fieldset>


                <br/>
                  <div style="border-style:solid;border-color:black;border-width:1px;background-color:#E0FFFF;width:350px;text-align:center">
                    <table width="300" >
                      <tr>
                        <td>DatoNuevo:</td>
                        <td><g:textField name="DatoNuevo" value="" /></td>
                      </tr>
                      <tr>
                        <td> &nbsp; </td>
                        <td><g:actionSubmit action="RecibirDato" value="Cargar"/></td>
                      </tr>
                    </table>
                  </div>
                </fieldset>


                <fieldset class="buttons">
                    <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>
