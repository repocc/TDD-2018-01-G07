<!DOCTYPE html>
<html lang="en">

<head>
	<title>Gráficos</title>
	 <!--Tag para pagina responsive-->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<asset:javascript src="jquery-2.2.0.min.js"/>
	<!--Grails emplea la libreria Jquery-2.2.0 se testea usando la misma
	<asset:javascript src="libreria_grafica/js/jquery_canvas/jquery-3.3.1.js"/>
	sino se usa la ultima actualizada------------>
	<asset:javascript src="libreria_grafica/js/jquery_canvas/jquery.canvasjs.min.js"/>
	<asset:javascript src="libreria_grafica/js/jquery_canvas/canvasjs.min.js"/>
	<asset:javascript src="libreria_grafica/js/chartjs/Chart.bundle.js"/>
	
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
	.contenedor  {vertical-align: middle;} <!--colocar img junto con body y verificar-->

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
<script>
	/* $ == JQuery*/
/* EVENTOS*/
/**para deshabilitar boton luego de pulsar boton polling*/
$('#boton_poll_dona_1').one('submit', function() {
    $(this).find('input[type="submit"]').attr('onclick','this.style.opacity = "0.6"; return false;')
    //.attr('disabled','disabled');
	$(this).find('input[type="submit"]').attr('onclick','this.style.opacity = "0.6"; return false;')
    
});

/****************************************/
/*
$(‘.clickme’).dblclick(function() {
	alert(‘You double-clicked on something.’);
});
*/
/*
$(‘#textbox2’).focus(function() {
alert(‘textbox2 has focus’);
});
*/
/* EVENTOS HOVER*/
/*
$(‘#mouseoverme’).hover(
	function() {
		$(‘#outputdiv’).text(‘You moused over the image.’);},
		function(){
			$(‘#outputdiv’).text(‘You moused out of the image.’);
		});
*/
function remplazarTexto (selector,texto){
/**Remplaza todos los div*/
	$(selector).text(texto) ;
};
/*
$(‘div’).append(‘ Lynn’);
$(“p”).replaceWith(“<div>I am a div</div>”) ;
$(“p”).replaceWith('<div style=background-color:#aaaaaa>' + $(“p”).html() + “</div>”) ;
*/
	</script>
	<script>
	
	/*COMUNICACION*/
var mensaje_estandard = 'Datos llegando...';

function convertirJsonString_a_Json (texto_json){
	JSON.parse(texto_json);
}; 


function solicitarDatos(url, selector,funcion_callback){

	var xmlhttp = new XMLHttpRequest();
	
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
				alert ("Falta indicar la funcion-callback");
				var dato = this.responseText;
				alert ("Dato arriba..." + dato);
				console.log(dato);
				funcion_callback(selector,dato);
		}
	};
xmlhttp.open("GET", url, true);
xmlhttp.send();

}
	
function solicitar_datos_y_dibujar(url_origen, selector, funcion_callback) {
	var sel = selector;
			    
  $.ajax({
		url: url_origen,
		contexto: sel,					//"${createLink(controller:'chart', action:'toppings')}",
		dataType: "json",
		async: true,			//por default es true, lo pongo por aclaración: asincrónico.
		})
		.done(function(jsonData,textStatus) {
			console.log('Datos llegando de Servidor status...' + textStatus);
			
			console.log ("AJAX dato selector: " + this.contexto );
			console.log ("AJAX dato json: " + jsonData.etiqueta );
			funcion_callback(this.contexto,jsonData);
		})
		
		.fail(function() {
				alert('Fallo cargar datos del grafico.');
		});
};	

	</script>
	
	<script>
	/*
		var nota = "Nota:";
		nota += " In JavaScript, keys can be strings, numbers, or identifier names:";
		nota += "---In JSON, values must be one of the following data types: a string,a number, an object (JSON object),an array, boolean o null.";
		alert(nota);
		var nota2 = "Nota: ";
		nota2 += "In JavaScript values can be all of the above, plus any other valid JavaScript expression, including:a function, a date,undefined";
		alert (nota2);
		var nota3 = "Nota: ";
		nota3 += "In JSON, string values must be written with double quotes:";
		nota3 += "{" + "name"+":" +"John" + "}";
		alert (nota3);
		var nota4= "Nota:";
		nota4 += "In JavaScript, you can write string values with double or single quotes:";
		nota4 += "{ name:'John' }";
		alert(nota4);
		var nota5 ="With JavaScript you can create an object and assign data to it, like this:";
		nota5 +=  'var person = { "name":"John", "age":31, "city":"New York" }';
		nota5 += "You can access a JavaScript object like this: ";
		nota5 += 'person.name -que retorna John-, o sino de esta forma person["name"]';
		alert(nota5);
		var nota6 = "Arrays con JSON:";
		nota6 += '{"name":"John","age":30,"cars":[ "Ford", "BMW", "Fiat" ]}';
		nota6 += "You access the array values by using the index number: x = myObj.cars[0]";
		alert (nota6);
		var nota7 = "Variante array de array en JSON: ";
		nota7 += 'myObj = {"name":"John","age":30,"cars": [{ "name":"Ford", "models":[ "Fiesta", "Focus", "Mustang" ] },{ "name":"BMW", "models":[ "320", "X3", "X5" ] },{ "name":"Fiat", "models":[ "500", "Panda" ] }]}';
		alert (nota7);
	
	*/
	</script>
	
	<script>
	
	/**Nota importante:
	Renderizar un modelo a Json a una vista concreta:
	Ejemplo: render(view: "/book/create", model: [bookInstance: bookInstance as JSON])
	*/
	/*CALLBACK y POOLLING*/
	/**Poll con JQUERY para dibujar */
function poll_Jquery(servidor,selector,funcion_callback) {
			
			/**CONSIDERAR EL USO DE $.getJSON(); $(selector).getJSON(url,data,success(data,status,xhr))*/
			/*	$.getJSON(url,data,success(data,status,xhr)
			
				url	Required. Specifies the url to send the request to
				data	Optional. Specifies data to be sent to the server
				success(data,status,xhr)	Optional. Specifies the function to run if the request succeeds
				Additional parameters:
				data - contains the data returned from the server.
				status - contains a string containing request status ("success", "notmodified", "error", "timeout", or "parsererror").
				xhr - contains the XMLHttpRequest object
				
				$.getJSON(url, data, function (data, status) {
					if (status === 200) {
						//Do stuff with the JSON data
					}
				});
			*/
			/**
			$.ajax({name:value, name:value, ... }) Recibe un mapa
			Otros parametros:
			xhr	A function used for creating the XMLHttpRequest objec.
			username	Specifies a username to be used in an HTTP access authentication request.
			success(result,status,xhr)	A function to be run when the request succeeds.
			jsonpCallback	Specifies a name for the callback function in a jsonp request.
			error(xhr,status,error)	A function to run if the request fails.
			*/
			
			var sel = selector; //sin esta variable no pasa directo de los parametros.
			setTimeout(function() {
			
				$.ajax({ 
					url			: servidor, 	//Specifies the URL to send the request to. Default is the current page.
					type		: "GET",		//Specifies the type of request. (GET or POST)
					contexto	: sel,
					success		: function (data) {
					
					
					console.log (data.valor);
					console.log (data.etiqueta);
					funcion_callback(this.contexto,data);},
					
					error: function(xhr){
								alert("Un error ha ocurrido: " + xhr.status + " " + xhr.statusText);},
								 
					dataType	: "json", 		//dataType	The data type expected of the server response.
					//complete	: poll_Jquery,
					timeout		: 10000			//The local timeout (in milliseconds) for the request
		 });
	}, 20000);/*Fin setTimeout*/
};

	
	</script>
	<script>
	var colorArray = ['#FF6633', '#FFB399', '#FF33FF', '#FFFF99', '#00B3E6', 
		  '#E6B333', '#3366E6', '#999966', '#99FF99', '#B34D4D',
		  '#80B300', '#809900', '#E6B3B3', '#6680B3', '#66991A', 
		  '#FF99E6', '#CCFF1A', '#FF1A66', '#E6331A', '#33FFCC',
		  '#66994D', '#B366CC', '#4D8000', '#B33300', '#CC80CC', 
		  '#66664D', '#991AFF', '#E666FF', '#4DB3FF', '#1AB399',
		  '#E666B3', '#33991A', '#CC9999', '#B3B31A', '#00E680', 
		  '#4D8066', '#809980', '#E6FF80', '#1AFF33', '#999933',
		  '#FF3380', '#CCCC00', '#66E64D', '#4D80CC', '#9900B3', 
		  '#E64D66', '#4DB380', '#FF4D4D', '#99E6E6', '#6666FF'];
		  
	function color_Random (){
		var rand = colorArray[Math.floor(Math.random() * colorArray.length)];
		
		return rand;
	}
	</script>
	<script>
		

	/*Funcionn grafica con CHART.JS*  DONA o TORTA/
/*****************************************************/
/*USAR JQUERy-charts con CANVAS.js PARA PORCENTAJES automatico --->>https://canvasjs.com/jquery-charts/doughnut-chart/*/
var dato_nulo = {
    datasets: [{
        data: [0],
        backgroundColor: ["#FF6384"],
        label: 'Set de datos' // for legend
    }],
    labels: ["Red"]};
	
var estructura_Datos_Libreria_CHARTJS = {
    datasets: [{
        data: [45, 25, 20, 10],
        backgroundColor: ['Red', 'Blue','Green', 'Yelow']
    }],
    labels: ['Red', 'Blue', 'Purple', 'Yellow']
};

var dona_grafico = null;
var contador = 0;

 /*************************************************/
 function iniciar_Configuracion_Dona(){
				//type: podria ser tambien 'pie', la misma estructura.
				//labels:esto son las etiquetas por valor 
		 
	var	 config = { 
			type: 'doughnut', 
						
			data: {
				datasets: [{
					
					data: [], 
				
				backgroundColor: [color_Random()], 
				
				
				label: 'SET DE DATOS #1 solo uno'}],
				
				
				labels	:[]
			},
			options:
				{
				responsive: true,
				legend: {
					position: 'top',
				},
				title: {
					display: true,
					text: 'Libreria -Chart.js- Dona Grafico'
				},
				animation: {
					animateScale: true,
					animateRotate: true
				}
				}
		};
		return config;
};

	/**********************************************************/
 
	 /**
	 Silent failures on $.getJSON calls: 
	 This might happen if, e.g., jsoncallback=json1234 has been used, 
	 while the function json1234() does not exist. 
	 In such cases the $.getJSON will silently error. 
	 We should therefore always use jsoncallback=? 
	 to let jQuery automatically handle the initial callback.
	 ERRORES posibles:
	 We have to make sure that the JSON returned by the server 
	 is in the correct format with the correct MIME-type being used.
	We can try to use $.get instead of $.getJSON as it might be that
	 our server returns invalid JSON. 
	 Also if JSON.parse() fails on the returned text we immediately 
	 know that the JSON is to blame.
	We can check the data that is being returned by logging it to the console. 
	This should then be the input for further investigations.
	 
	 */
var configuracion_dona_chartjs = null;
 
var dibujar_Dona_chartjs = function dibujarDona(selector,dato_json) {
   /*LIBRERIA CHART.JS*/ 
   /*http://www.chartjs.org/samples/latest/charts/doughnut.html*/
   console.log(dato_json.etiqueta);
   console.log (dato_json.valor);
   console.log(dona_grafico);
   console.log(selector);
   
   if (dona_grafico == null){
			/**Dibuja un dona sin datos pero configurado el "dataset"*/
		   configuracion_dona_chartjs = iniciar_Configuracion_Dona();
		   console.log(configuracion_dona_chartjs);
	
	
		var graficar =	function(selector,dato_json,configuracion_dona_chartjs) {
								
								console.log(selector);
								var ctx = document.getElementById(selector).getContext('2d');
								console.log("Valor de ctx " + ctx);
								dona_grafico = new Chart(ctx, configuracion_dona_chartjs);
								//agrego atributo a window , que es un objeto global de JS y es la ventana del navegador tambien
								console.log(dona_grafico);
								
								if (dato_json != null){
									
									agregarDato_torta(configuracion_dona_chartjs,selector,dato_json);
								};	
					
						};//fin funcion interna
					graficar(selector,dato_json,configuracion_dona_chartjs);
					dona_grafico.update(); 
	}else{
		/*agregar datos*/
		console.log("Agrega dato");
		agregarDato_torta(configuracion_dona_chartjs,selector, dato_json);
		
			dona_grafico.update();
			return; 
		}
	
	console.log("SALIENDO.");
};


function agregarDato_torta(config, selector,dato_json){
	/*Se agregan al la configuracion los datos por separado.
	No forman parte del objeto graficador*/
	
	if (dona_grafico == null){
		var ctx = document.getElementById(selector).getContext('2d');
		console.log("Valor de ctx en agregar dato() " + ctx);
		dona_grafico = new Chart(ctx, iniciar_Configuracion_Dona());
								
	}
	
	contador += contador;
	var cero = 0;
	var etiqueta = "Dato #" + contador;
	var color_rnd = color_Random();
	console.log
	config.data.datasets[cero].data.push(dato_json.valor);
	config.data.datasets[cero].backgroundColor.push(color_rnd);
	
	config.data.labels.push(dato_json.etiqueta);

			      
};

	</script>
</head>

<body>
	
	
 <div class="contenedor">
			 <h1>Gráficos</h1>	
			 <div id="eventos_test">
				 <asset:image src="imagenes/body_fondo/body2.jpg" absolute="true"/>
				 <div id=”mouseoverme”>Este texto cambiará.</div>
			 </div>	
  <!--<img src="/w3images/notebook.jpg" alt="Notebook" style="width:100%;hight:100%">-->
  <div class="contenido">
    
    
<!------------------CONTENEDOR DE GRAFICOS----------------------------->
    <div class="contenedor_Grafico">
		<div class="panel_header">Dona con boton</div>
			<div class="panel_body">
<!---------------Graficos---------------------------------------------->	

<!---------------Graficos 1---------------------------------------------->
<!--Responsive Charts:the canvas render size does not adjust automatically based on the display size, making the rendering inaccurate.-->

				<div id="cont_grafico_1">
						
						<div id="cont_grafico_dona_1"style="position: relative; height:80vh; width:80vw">
						
							<canvas id="dona_1"></canvas>
						</div><!--cont_grafico__dona_1-->
						
						<div type="submit" class="btn_dona" id="botones_dona_1">
							<button type="button" class="btn_dona" id="boton_dona_1"
							onClick="dibujar_Dona_chartjs('dona_1',{ valor: 25, etiqueta: 'valorSimuladoFijo'})">Agregar Datos Simulando</button>
							
							<button type="button" class="btn_dona" id="boton_GetOnlyOne_dona_1"
							onClick="solicitar_datos_y_dibujar('http://localhost:8080/ticket/enviar','dona_1',dibujar_Dona_chartjs)">solicitar Datos Uno a la vez-</button>
							
							<button type="button" class="btn_dona" id="boton_poll_dona_1"
							onClick="poll_Jquery('http://localhost:8080/ticket/enviar','dona_1',dibujar_Dona_chartjs)">solicitar Datos indiscriminadamente POLLING-Deshabilitara sendos botones-</button>
						
						</div><!--botones_dona_1-->

				</div><!--cont_grafico_1-->
<!---------------Graficos 1 FIN---------------------------------------->				
				
								
<!-------------Fin Graficos-------------------------------------------->	
				
			</div><!--panel_body-->
		</div><!--panel_header-->
	</div><!--contenedor_Grafico-->
	
<!-------------Fin CONTENEDOR Graficos-------------------------------------------->	
    <h3>Monitor Eventos</h3>
    
    <h4>Tickets-Testeo Comunicacion-Callback-Polling-JS-GRAILS: THE WEB LAYER (fuente principal)-</h4>
	
	<div class="boton" id ="derivacion_botones">
		<g:form name="miForm_1" url="[action:'iniciar',controller:'publicador' ]" id="form_1">
			
			<g:actionSubmit value="volver"/>
			
		</g:form>
		<g:form name="miForm_2" url="[action:'derivar_inicioGrails',controller:'publicador']" id="form_2">
		
			<g:actionSubmit value="Inicio Grails"/>
		
		</g:form>
	</div><!--Fin derivacion_botones-->
  </div><!--Fin contenido-->
</div><!--Fin contenedor-->

</body>

</html>
