<!DOCTYPE html>
<html lang="en">

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Monitor Eventos</title>
	<style>
	
	* {
    box-sizing: border-box;	
	
	body,html 	{
		
		margin: 10%;
		font-family: Arial;
		font-size: 17px;		
			
		background-image: url("assets/imagenes/body_fondo/body1.jpg");
		background-repeat: no-repeat;
		background-attachment: fixed;
		background-size: 100% 100%;
    }
	.contenedor, #imagen_fondo  {vertical-align: middle;} <!--colocar img junto con body y verificar-->

	.contenido {
		text-align: center;
		position:absolute;
		bottom: 1%;
		background: rgba(0, 0, 0, 0.5); /* Black background with transparency */
		color: #f1f1f1;
		width: 100%;
		padding: 20px;
		background-size: cover;
	}
	</style>
	
</head>

<body>
	<asset:stylesheet src="application.css"/>

    <g:layoutHead/>
	<g:if test="${flash.mensaje}">
			<div class="message">
				<h3>${flash.mensaje}</h3>
			</div>
     </g:if>
     <g:if test="${session.usuario_id}">
			<br/>
			<div class="message"><h2>Sesion iniciada como: ${session.usuario_id} ${session.usuario_nombre} | <g:link action='cerrarSesion'>Cerrar sesión</g:link></h2></div>
     </g:if>
      
	<div class="contenedor">
<!------------------CONTENEDOR----------------------------->		
	<!--	<div class"contenedor"id="imagen_fondo">
				<asset:image src="imagenes/body_fondo/body1.jpg" absolute="true" />
	-->
	<!--	</div>	-->		<!--Fin imagen_fondo-->
		
		<div class="contenido">
	
<!------------------CONTENIDO----------------------------->	 
			<div class="contenido jumbotron" id="mensaje_inicioMonitor">
				<h2>Monitor de eventos-Distribuidor-Consultor</h2>
				<h3>¡Bienvenido!</h3>
			</div><!--Fin mensaje_inicioMonitor-->
	
	
			<div class="btn" id ="derivacion_botones">
				<g:form name="miForm" controller="publicador">
<!--------------------------------------------------------------------->					
					<div class="message">
						<h2>Usuario</h2></div>
					<div class="btn-group">
					<g:link controller="usuario" action="index"><input type="button" value="Índice" class="btn btn-primary" /></g:link>	
					<g:link controller="usuario" action="create"><input type="button" value="Registración" class="btn btn-primary" /></g:link>	
					</div>
					
					<div class="message">
						<h2>Login de usuarios</h2></div>
					<div class="btn-group">
						<g:link controller="autenticador" action="iniciarSesion"><input type="button" value="Ingresar[Login][iniciarSesion]" class="btn btn-primary" /></g:link>	
						<g:link controller="autenticador" action="cerrarSesion"><input type="button" value="Salir [Logout][CerrarSesion]" class="btn btn-primary" /></g:link>	
					</div>
<!--------------------------------------------------------------------->
					<div class="message">
						<h2>Distribuidor-Proveedor de Tickets-</h2>
						<h4>[NOTA] Los tickets generados para ser procesados por el ConsumidorFinal(Visualiza gráficamente):</h4>
						<br/>
						<h4>El enlace es: [HOSTNAME]/ticket/solicitarDatos</h4>
					
					</div>
						
					<div class="btn-group">
					<g:link controller="ticket" action="index"><input type="button" value="Índice" class="btn btn-primary" /></g:link>	
					<g:link controller="ticket" action="create"><input type="button" value="Crear ticket" class="btn btn-primary" /></g:link>	
					
					</div>
<!--------------------------------------------------------------------->					
					<div class="message"><h2>Flujo de Estados</h2></div>
					<div class="btn-group">
					<g:link controller="flujo" action="index"><input type="button" value="Índice" class="btn btn-primary" /></g:link>	
					<g:link controller="flujo" action="create"><input type="button" value="Crear flujo" class="btn btn-primary" /></g:link>	
					
					</div>
<!--------------------------------------------------------------------->					
					<div class="message"><h2>Estados</h2></div>
					<div class="btn-group">
					<g:link controller="estado" action="index"><input type="button" value="Índice" class="btn btn-primary" /></g:link>	
					<g:link controller="estado" action="create"><input type="button" value="Crear estado" class="btn btn-primary" /></g:link>	
					
					</div>
<!--------------------------------------------------------------------->					
					<div class="message"><h2>Consultor-Generador de reglas-</h2></div>
					<div class="btn">
					<g:link controller="regla" action="index"><input type="button" value="Índice" class="btn btn-primary" /></g:link>	
					<div class="message"><h4>Seleccionar la regla que será utilizada para configurar el Motor-APICLOJURE:</h4></div>			
					<g:link controller="regla" action="seleccionarRegla"><input type="button" value="Selecionar Regla" class="btn btn-primary" /></g:link>	
					
					</div>
<!--------------------------------------------------------------------->
					<div class="message"><h2>Tickets generados para pruebas</h2></div>
					<div class="btn-group">
					<g:link uri="/ticketsmock"><input type="button" value="Tickets [JSON]" class="btn btn-primary" /></g:link>	
					<g:link uri="/ticketsmock.xml"><input type="button" value="Tickets [XML]" class="btn btn-primary" /></g:link>	
					
					</div>
<!--------------------------------------------------------------------->			
					<div class="message"><h2>Regla de prueba</h2></div>
					
					<div class="btn-group">
						<g:link controller="regla" action="show/1"><input type="button" value="Regla de pruebas" class="btn btn-primary" /></g:link>	
					</div>
<!--------------------------------------------------------------------->
					<div class="message">
						<h2>Modelo de Funcionamiento-Pruebe el funcionamiento-</h2>
						<h3>Tickets y reglas generados se procesan con MOTOR-APICLOJURE-.</h3>
						<h4>Esta misma acción puede ser realizada y visualizada a través del consumidor final.</h4>
						<h4>[NOTA] url a ejecutar desde consumidor final : [Hostname]/ticket/enviarTicketsMockProcesados" </h4>
					</div>
					<div class="message"><h4>[Caso LocalHost]-[PULSAR F5 o refresh para actualizar cada procesamiento del ticket -VISTA ESTÁTICA EN SERVIDOR-DATOS PLANOS-]</h4>
						<h5>[NOTA] El proceso es cíclico. Terminados los tickets. Se regeneran y vuelve a empezar.</h5>
						</div>					
					<div class="btn">
						<g:link controller="ticket" action="enviarTicketsMockProcesados"><input type="button" value="Procesar Tickets [Prueba][Localhost][SALIDA DATOS PLANOS]" class="btn btn-primary" /></g:link>
					</div>
<!--------------------------------------------------------------------->
					<div class="message"><h2>Registros de inicio/cierre sesión de cada usuario [anulado opcion creacion web]</h2></div>
						<g:link controller="autenticador" action="index"><input type="button" value="Índice" class="btn btn-primary" /></g:link>	
					<div class="btn-group">
					
					</div>
<!--------------------------------------------------------------------->					
					<div class="message"><h2>Acceso a la Consola de base de datos (activa en desarrollo-no producción)</h2></div>
					<div class="btn-group">
					<g:link uri="/dbconsole">
						<button type="button" class="btn btn-link" >Consola Base de Datos (localhost)</button>
					</g:link>
					</div>
<!--------------------------------------------------------------------->					
					
					<div class="message jumbotron">
						<h2>Alojamiento web</h2>
						<h3>Acceda a la aplicación Monitor-Proveedor-Generador-Concentrador alojada en Heroku o a la aplicación ConsumidorFinal-Cliente-Visualización de gráficos- alojadas en Heroku</h3>
					</div>						
					<div class="btn-group">
						<g:link url="https://rodriguezmonitor7510-g7-dev.herokuapp.com">
							<button type="button" class="btn btn-link" >Concentrador-Distribuidor</button>
						</g:link>
						<g:link url="https://rodriguez-cliente-7510-g7.herokuapp.com">
							<button type="button" class="btn btn-link" >ConsumidorFinal</button>
						</g:link>
					
						<div class="message jumbotron">
							<h2>Acceso consola de aplicación Monitor en  hosting</h2>
							<h4>Nota: En en el campo "JDBC URL" ingresar:jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000 </h3>
						</div>
						<g:link url="https://rodriguezmonitor7510-g7-dev.herokuapp.com/dbconsole">
							<button type="button" class="btn btn-link" >Acceder Consola web</button>
						</g:link>
					</div>
<!--------------------------------------------------------------------->
					<div class="btn btn-group btn-warning">
						<g:actionSubmit value="Inicio Grails" action="derivar_InicioGrails"class="btn btn-primary"/>
						<g:actionSubmit value="Estrategia_1" action="derivar_estrategia_1" class="btn btn-primary"/>
						<g:actionSubmit value="Estrategia_2" action="derivar_estrategia_2"class="btn btn-primary"/>
					</div>
				</g:form>
				
			</div><!--Fin derivacion_botones-->
		
<!------------------CONTENIDO FIN----------------------------->	
		</div><!--Fin contenido-->
 <!------------------CONTENEDOR FIN-----------------------------> 
	</div><!--Fin contenedor-->
	 <asset:javascript src="application.js"/>
</body>

</html>
