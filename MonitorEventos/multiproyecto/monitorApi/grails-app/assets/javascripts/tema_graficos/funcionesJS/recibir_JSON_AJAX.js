

    /**
    * This example callback simple prints the result of the AJAX call.
    
    function myCallback (result)
    {
        p(result);
    }

    RGraph.AJAX(url, myCallback);
    RGraph.AJAX.getString(url, myCallback);
    RGraph.AJAX.getNumber(url, myCallback);
    RGraph.AJAX.getCSV(url, myCallback);
    RGraph.AJAX.getJSON(url, myCallback);
    
    // The POST function allows you to make POST requests to your server - sending data along with it. The
    // data argument should be an object with key/value pairs eg. {'name': 'Richard Hargreaves','age': 22}
    // The data is encoded for you using encodeURIComponent()
    RGraph.AJAX.POST(url, data, callback);
	*/
	/** removiendo nodos hijos a partir del padre
	 *  <div id="div1">
<p id="p1">This is a paragraph.</p>
<p id="p2">This is another paragraph.</p>
</div>

<script>
var parent = document.getElementById("div1");
var child = document.getElementById("p1");
parent.removeChild(child);
</script> */

/*******Esto sirve en la web page. Habría que usar otra estrategia donde hace post
 * el y se recibe directamente en la GSP y se dibuja alli*/	
var url_dato_JSON = "${createLink(controller:'publicador', action:'publicar')}"	
 /**La otra manera es realizar solicitudes y esperar respuesta. Esa estrategia se asume.
  * Es correcta? 
  * */
  var url = 'localhost:8080/ticket/procesar'
  /*Petición de datos a la acción (ACTION))del controlador Publicador.*/
 window.onload = function ()
    {
		
        //RGraph.AJAX.getJSON
        RGraph.SVG.AJAX.getJSON(url, dibujar_Dona_Ajax);
        console.log("DATO LLEGO");
        alert ("LLEGO DATO.");
    }	
