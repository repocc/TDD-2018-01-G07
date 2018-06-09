<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'tablero.label', default: 'Tablero')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#edit-tablero" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="edit-tablero" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.tablero}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.tablero}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.tablero}" method="PUT">
                <g:hiddenField name="version" value="${this.tablero?.version}" />
                <fieldset class="form">
                    <f:all bean="tablero"/>
                </fieldset>

                <br/>
                  <div style="border-style:solid;border-color:black;border-width:1px;background-color:#E0FFFF;width:350px;text-align:center">
                    <table width="300" >
                      <td>Nuevo Mensaje Predifinido:</td>
                      <tr>
                        <td> &nbsp; </td>
                        <td><g:actionSubmit action="RecibirSpam" value="spam"/></td>
                      </tr>
                      <tr>
                        <td> &nbsp; </td>
                        <td><g:actionSubmit action="RecibirNoSpam" value="noSpam"/></td>
                      </tr>
                    </table>
                  </div>
                </fieldset>

                <br/>
                  <div style="border-style:solid;border-color:black;border-width:1px;background-color:#E0FFFF;width:350px;text-align:center">
                    <table width="300" >
                      <td>Nuevo No Predifinido:</td>
                      <tr>
                        <g:textField name="mensaje" value="ingres el mensaje aqui" />
                        <td> &nbsp; </td>
                        <td><g:actionSubmit action="mensajaNoDefinido" value="enviar"/></td>
                      </tr>
                    </table>
                  </div>
                </fieldset>

              <br/>
              <div style="border-style:solid;border-color:black;border-width:1px;background-color:#E0FFFF;width:350px;text-align:center">
                          <table width="300" >
                              <td>Nuevo Insturmento:</td>
                            <tr>
                              <td>TiposDeGraficos:</td>
                              <td>
                                <g:select name="TiposDeGraficos" from="${['Derecha', 'Izquierda']}" />
                              </td>
                            </tr>
                            <tr>
                              <td>TiposDeConteo:</td>
                              <td>
                                <g:select name="TiposDeConteo" from="${['SoloSpam', 'TodoLosMails', 'Inicio', 'Fin']}" />
                              </td>
                            </tr>
                            <tr>
                              <td> &nbsp; </td>
                              <td><g:actionSubmit action="agregarTablero" value="Crear"/></td>
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
