<!DOCTYPE html>
<html lang="en">

<head>
	<title>Monitor Eventos</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<style>
	
	* {
    box-sizing: border-box;	
	
	body #imagen_fondo {
		margin: 1%;
		font-family: Arial;
		font-size: 17px;		
		<!--background-image: url("img_tree.gif");-->
		background-repeat: no-repeat;
		background-attachment: fixed;
		background-size:100%;
	}
	.contenedor img {vertical-align: middle;} <!--colocar img junto con body y verificar-->

	.contenido {
		position:absolute;
		bottom: 10%;
		background: rgba(0, 0, 0, 0.5); /* Black background with transparency */
		color: #f1f1f1;
		width: 100%;
		padding: 20px;
		background-size: cover;
	}
	</style>
</head>

	
<body>
	<div class="contenedor">
<!------------------CONTENEDOR----------------------------->		
		<div class"contenedor"id="imagen_fondo">
			<g:img dir="images/imagenes/body_fondo" file="body1.jpg" />
		</div><!--Fin imagen_fondo-->
		
		<div class="contenido">
<!------------------CONTENIDO----------------------------->	 
			<div class="contenido" id="mensaje_inicioMonitor">
				<h1>Monitor de eventos</h1>
				<p>Bienvenido</p>
			</div><!--Fin mensaje_inicioMonitor-->
	
	
			<div class="boton" id ="derivacion_botones">
				<g:form name="miForm" controller="publicador">
					<g:actionSubmit value="Estrategia_1" action="derivar_estrategia_1" />
					<g:actionSubmit value="Estrategia_2" action="derivar_estrategia_2"/>
					<g:actionSubmit value="Tickets_Test" action="derivar_TicketController"/>
					<g:actionSubmit value="Tickets_JSON" action="derivar_TicketShow"/>
					<g:actionSubmit value="Inicio Grails" action="derivar_Reglas"/>
					<g:actionSubmit value="Inicio Grails" action="derivar_inicioGrails"/>
				</g:form>
			</div><!--Fin derivacion_botones-->
		
<!------------------CONTENIDO FIN----------------------------->	
		</div><!--Fin contenido-->
 <!------------------CONTENEDOR FIN-----------------------------> 
	</div><!--Fin contenedor-->

</body>

</html>
