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
			<asset:image src="imagenes/body_fondo_lisos/blog-2.jpg" absolute="true"/>
		</div><!--Fin imagen_fondo-->
		
		<div class="contenido">
<!------------------CONTENIDO----------------------------->	 
			<div class="contenido" id="mensaje_inicioMonitor">
				<h1>Monitor de eventos</h1>
				<h2>Estrategia 2</h2>
			</div><!--Fin mensaje_inicioMonitor-->
	
	
			<div class="boton" id ="derivacion_botones">
				<g:form name="miForm_1" controller="estratega" id="form_1">
					
					<g:actionSubmit value="volver" action="derivar_IniciarMonitor"/>
					
				
					<g:actionSubmit value="Inicio Grails" action='derivar_InicioGrails'/>
				
				</g:form>
			</div><!--Fin derivacion_botones-->
<!------------------CONTENIDO FIN----------------------------->	
		</div><!--Fin contenido-->
 <!------------------CONTENEDOR FIN-----------------------------> 
	</div><!--Fin contenedor-->

</body>

</html>
