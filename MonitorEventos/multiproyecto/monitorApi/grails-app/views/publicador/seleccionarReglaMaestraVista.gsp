<!DOCTYPE html>
<html lang="en">

<head>
	<title>Monitor Eventos</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<style>
	
	* {
    box-sizing: border-box;	
	
	body {
		margin: 10%;
		font-family: Arial;
		font-size: 17px;		
		<!--background-image: url("img_tree.gif");-->
		background-repeat: no-repeat;
		background-attachment: fixed;
		background-size:100%;
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
	
	table, th, td {
    border: 1px solid black;
}
table {
    font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 100%;
}
	</style>
	
</head>
	
<body>
	<div class="contenedor">
<!------------------CONTENEDOR----------------------------->		
		<div class"contenedor"id="imagen_fondo">
			<asset:image src="imagenes/body_fondo/body1.jpg" absolute="true" />
		</div><!--Fin imagen_fondo-->
		
		<div class="contenido">
<!------------------CONTENIDO----------------------------->	 
			<div class="contenido" id="mensaje_inicioMonitor">
				<h1>Reglamento</h1>
				<p></p>
			</div><!--Fin mensaje_inicioMonitor-->
	
	
			<g:set var="fecha" value="${new Date()}" />
				
					   
         <g:form name="formulario_regla" controller="Regla" method="PUT">   
                 
           
                 <h1>Seleccionar Regla Principal </h1>
                 <h2>${fecha}</h2>
                 <table style="border: 1px solid black;
font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 100%">
                 		
				             <g:each in="${reglas}">
												
												<tr style="border: 1px solid black">
												<th>Nombre</th>
												<td><span>${it.nombre}</span></td>
												</tr
												<tr style="border: 1px solid black">
												<th>Descripcion</th>
												<td><span>${it.regla}</span></td>
												</tr>
											</g:each>
									 </table>            
        			<fieldset class="form">
                 <div style="background-color:lightgreen; width: 50vw;margin:10vw">
                    
                    <label for="reglas">Seleccionar Regla para motor-APIClojure-</label>
                    <g:select name="regla"from="${reglas}"	size="5"  optionKey="id" 														multiple="false"	value="${reglas}" />
       						</div>	             
							</fieldset>
          
           <fieldset class="buttons">
                    <g:actionSubmit  value="seleccionar" action="seleccionarRegla" />
           					<g:actionSubmit value="Inicio Monitor" action="derivar_InicioMonitor"/>
           </fieldset>
         
         </g:form>
					
					
					
				
				
			</div><!--Fin derivacion_botones-->
		
<!------------------CONTENIDO FIN----------------------------->	
		</div><!--Fin contenido-->
 <!------------------CONTENEDOR FIN-----------------------------> 
	</div><!--Fin contenedor-->

</body>

</html>
