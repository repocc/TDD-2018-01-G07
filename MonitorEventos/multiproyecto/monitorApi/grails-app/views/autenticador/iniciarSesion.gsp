<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <title><span></span>INICIO DE SESION</span></title>
    </head>
    <body>
        <g:if test="${flash.mensaje}">
			<div class="message">
				<h3>${flash.mensaje}</h3>
			</div>
        </g:if>
        <g:if test="${session.usuario_id}">
			<br/>
			<div class="message"><h2>Sesion iniciada como: ${session.usuario_id} ${session.usuario_nombre} | <g:link action='cerrarSesion'>Cerrar sesión</g:link></h2></div>
        </g:if>
        <g:else>
			<g:form action='iniciarSesion' style="padding-left:200px">
				<div style="width:220px">
					
					<label>Nombre Registración</label><g:textField name="nombreId" value="" />
					<label>Clave de acceso</label><g:passwordField name="clave" value="" />
					<label>&nbsp;</label><g:submitButton name="Autenticarse" value="iniciarSesion" />
				</div>
			</g:form>
        </g:else>
    </body>
</html>
