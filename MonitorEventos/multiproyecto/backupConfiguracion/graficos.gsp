<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    

    <title>DASHBOARD</title>

    
  </head>

<body>

  <section id="container" class="">
      
      <!--main content start-->
      <section id="main-content" style="margin-left: 210px;">
          <section class="wrapper">
          
              <!-- page start-->
              
		 <div class="content-panel" style="text-align:center">
			<h3>Gráficos</h3>
					  
			<h4> Dona</h4>
                                  
      <!-- **********************************************************************************************************************************************************
      AQUI ESTA LA DONA Y EL BOTON
*********************************************************************************************************************************************************** -->
			 <div class="panel-body text-center" style="text-align:center">
				  
						<canvas id="dona" height="300" width="400" style="width: 400px; height: 300px;"></canvas>
						<p> Boton temporal para que se grafique</p>
						<button type="button" class="btn"
						onClick="dibujarDona('dona')">Mostrar dona</button>
					
				</div>
                              
    <!-- **********************************************************************************************************************************************************
      AQUI ESTA LA DONA Y EL BOTON DUPLICADO 
      *********************************************************************************************************************************************************** -->
                             
			 <div class="panel-body text-center" style="text-align:center">
						  
						  <canvas id="dona_2" height="300" width="400" style="width: 400px; height: 300px;"></canvas>
							
							<p> Boton temporal para que se grafique</p>
							
							<button type="button" class="btn"
							onClick="dibujarDona('dona_2')">Mostrar dona</button>
							
				</div>	
	<!-- **********************************************************************************************************************************************************
      AQUI ESTA LA DONA  AJAX
      *********************************************************************************************************************************************************** -->
			 <div class="panel-body text-center" style="text-align:center">
						<div id="dona_ajax" style="text-align:center;border:1px solid red">
							<canvas id="dona_3" height="300" width="400" style="width: 400px; height: 300px;"></canvas>
						</div>
				
				</div>	
	                 
	<!-- **********************************************************************************************************************************************************
        AJAX
      *********************************************************************************************************************************************************** -->
		
				<div class="panel" style="text-align:center">
						<div id="dona_ajax" style="text-align:center;border:1px solid red">
							<canvas id="dona_4" height="300" width="400" style="width: 400px; height: 300px;"></canvas>
						</div>
								
						<button type="button" class="btn"
							 onClick="displayChart('localhost:8080/ticket/enviar', '#dona_4')">Solicitar Dato</button>
							
				</div>	
				
						<div class="panel" style="text-align:center">
						<div id="barras_ajax" style="text-align:center;border:1px solid red">
							<canvas id="barras" height="300" width="400" style="width: 400px; height: 300px;"></canvas>
						</div>
								                    
			 
			 
			 <div class="content-panel" style="text-align:center">
				  <h4>Línea</h4>
				  <div class="panel-body text-center" style="text-align:center;border:1px solid red">
					  <canvas id="line" height="300" width="400" style="width: 400px; height: 300px;"></canvas>
				  </div>
			  </div>
                                  			  
			 <div class="content-panel" style="text-align:center">
				  <h4>Barrras</h4>
				  <div class="panel-body text-center"style="text-align:center;border:1px solid red">
					  <canvas id="bar" height="300" width="400" style="width: 400px; height: 300px;"></canvas>
				  </div>
              </div>                      
              
			 <div class="content-panel" style="text-align:center">
				  <h4> Torta</h4>
				  <div class="panel-body text-center" style="text-align:center;border:1px solid red">
					  <canvas id="pie" height="300" width="400" style="width: 400px; height: 300px;"></canvas>
				  </div>
			  </div>
                      
                  
          </div>   
              <!-- page end-->
          </section>          
      </section><!-- /MAIN CONTENT -->

     
      <!--footer start-->
      <footer class="site-footer">
          <div class="text-center"style="text-align:center">
              2018 - Argentina
              <a href="procesar.gsp#" class="go-top">                  
              </a>
          </div>
      </footer>
      <!--footer end-->
  </section>

    <!-- js placed at the end of the document so the pages load faster -->
    <asset:javascript src="tema_graficos/js/actualizadas/jquery-3.3.1.js"/>
    <asset:javascript src="tema_graficos/js/actualizadas/canvasjs.min.js.js"/>

    <!--common script for all pages-->
    <!--script for this page-->
    <asset:javascript src="tema_graficos/funcionesjs/utilidad_constantes_colores_meses.js"/>
     <asset:javascript src="tema_graficos/js/chart-master/Chart.js"/>
    
    <!-- Para cargar los datos de los gráficos-->
    <asset:javascript src="tema_graficos/js/chartjs-conf.js"/>
        <!--Libreria RGRAPH-->
    <!--Aprovechando funcionalidad simplificada AJAX para comunicacion-->
    <!--Tambien para graficos SVG-->
    
    
    <asset:javascript src="tema_graficos/js/RGraph/libraries/RGraph.svg.common.ajax.js"/>
    <asset:javascript src="tema_graficos/js/RGraph/libraries/RGraph.svg.common.core.js"/>
    <asset:javascript src="tema_graficos/funcionesJS/dibujar_Dona.js"/>
	<asset:javascript src="tema_graficos/funcionesJS/dibujar_Dona_AJAX.js"/>
	<asset:javascript src="tema_graficos/funcionesJS/otra_dona.js"/>
	<asset:javascript src="tema_graficos/funcionesJS/recibir_JSON_AJAX.js"/>
	<asset:javascript src="tema_graficos/funcionesJS/dibujar_barra.js"/>
	

</body>
</html>
