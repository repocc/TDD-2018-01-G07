

var mi_grafico_Dona = null;
window.mi_grafico_Dona = mi_grafico_Dona;

var selector ="dona_3"

//en milisegundos.
var configuracion_update = {
	duration: 800, 
    easing: 'easeOutBounce'
}
//dato nulo al inicio si no hay datos correctos.
var dato_nulo = [{
            value: 0,
            color:"#1abc9c"
        }] 

var default_colors = ['#3366CC','#DC3912','#FF9900','#109618','#990099','#3B3EAC','#0099C6','#DD4477','#66AA00','#B82E2E','#316395','#994499','#22AA99','#AAAA11','#6633CC','#E67300','#8B0707','#329262','#5574A6','#3B3EAC'];

var nombre_Colores_basicos = [
						window.chartColors.red,
						window.chartColors.orange,
						window.chartColors.yellow,
						window.chartColors.green,
						window.chartColors.blue,
					];
/*******************************************************/
/*Cartilla de colores*/
var nombre_Colores = Object.keys(window.chartColors);
/******************************************************/
var datos_reservados = [
        {
            value: 30,
            color:"#1abc9c"
        },
        {
            value : 50,
            color : "#2ecc71"
        },
        {
            value : 100,
            color : "#3498db"
        },
        {
            value : 40,
            color : "#9b59b6"
        },
        {
            value : 120,
            color : "#34495e"
        }

    ];
   /********************************************/ 
    var configuracion = {
			type: 'doughnut',
			data: {
				datasets: [{
					data: [					
					],
					backgroundColor: [			
					],
					label: 'Dataset 1'
				}],
				labels: []
			},
			options: {
				responsive: true,
				legend: {
					position: 'top',
				},
				title: {
					display: true,
					text: 'GRAFICO Dona'
				},
				animation: {
					animateScale: true,
					animateRotate: true
				}
			}
		};

/*******************************************************/

/***
 * Ajax get id of element where there are multiple links with elements:
You need to bind the ajax call to a click handler:

$(document).on("click","a",function(e){
    $.ajax({  
    type: "POST",  
    data: "id=" + $(this).attr("id"), 
    url: "vote.php"
    });
});

*/
/******************************************************/
function addData(chart, label, data) {
    chart.data.labels.push(label);
    chart.data.datasets.forEach((dataset) => {
        dataset.data.push(data);
    });
    chart.update();
}

function agregarDato(dato) {
	//el dato agregado es un numero. Tambien se considera en esta prueba que no llega etiqueta.Sino se modifica.	
			if (configuracion.data.datasets.length > 0) {
				
				configuracion.data.labels.push('dato #' + configuracion.data.labels.length);
				
				//obtengo el color de una posicion del array calculando el modulo.
				var nombre_Color = nombre_Colores[configuracion.data.datasets[0].data.length % nombre_Colores.length];
				
				//el nombre es clave y obtengo el valor de la cartilla de colores en hexadecimal. (Creo).
				var nuevo_Color = window.chartColors[nombre_Color];
				
				//agrego-se supone solo un dato a la vez que llega remotamente.
				//De esta manera
				configuracion.data.datasets.forEach( function(dataset) {
					dataset.data.push(dato);
					dataset.backgroundColor.push(nuevo_ColorColor);
				});

				window.mi_grafico_Dona.update(configuracion_update);
			}
/******************************************************/
/**
 * The following properties are supported:

    duration (number): Time for the animation of the redraw in milliseconds
    lazy (boolean): If true, the animation can be interrupted by other animations
    easing (string): The animation easing function. See Animation Easing for possible values.

Example:

myChart.update({
    duration: 800,
    easing: 'easeOutBounce'
})
*
* Preventing Animations

Sometimes when a chart updates, you may not want an animation. 
* To achieve this you can call update with a duration of 0. 
* This will render the chart synchronously and without an animation.
* 
* /
/*******************************************************/
/***COMO ACCEDER A UN JSON:
 * 
 * You can access the object values by using dot (.) notation:
Example
myObj = { "name":"John", "age":30, "car":null };
x = myObj.name;
 * 
 * 
 * You can also access the object values by using bracket ([]) notation:
Example
myObj = { "name":"John", "age":30, "car":null };
x = myObj["name"];
* 
* *******************************************************
* Arrays in JSON Objects

Arrays can be values of an object property:
Example
{
"name":"John",
"age":30,
"cars":[ "Ford", "BMW", "Fiat" ]
}
Accessing Array Values

You access the array values by using the index number:
Example
x = myObj.cars[0];
* ***/


/*******************************************************/
function dibujar_Dona_Ajax(dato_json) {
	
		if (mi_grafico_Dona != null){
			//agregar dato nuevo
			var valor = dato_json.value; //segun seteado prueba en controlador publicar.
			
			agregarDato(objeto_grafico_de_window,dato_json.value);
			 
			}
		else{
			
			//no existe objeto grafico, primera vez 
			var ctx = document.getElementById(selector).getContext('2d');
			//se crea un objeto de tipo propiedad ,javascript, a la pagina (alias "window"") 
			//se crea el objeto grafico y se asigna a la propiedad.
			window.mi_grafico_Dona = new Chart(ctx, configuracion);
			
			};
				
};
