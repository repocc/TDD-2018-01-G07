<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    

    <title>DASHBOARD Personal</title>

    
  </head>

<body>

  <section id="container" class="">
      <!-- **********************************************************************************************************************************************************
      TOP BAR CONTENT & NOTIFICATIONS
      *********************************************************************************************************************************************************** -->
      <!--header start-->
      <!--header end-->
      
      <!-- **********************************************************************************************************************************************************
      MAIN SIDEBAR MENU
      *********************************************************************************************************************************************************** -->
      <!--sidebar start-->
      <aside>
          <div id="sidebar" class="nav-collapse " style="overflow: hidden; margin-left: 0px;" tabindex="5000">
              <!-- sidebar menu start-->
              <ul class="sidebar-menu" id="nav-accordion" style="display: block;">
              
              	  
              	  <h5 class="centered">Técnicas de Diseño</h5>
              	  	
                  
                          <span>Dashboard</span>
                    

              <!-- sidebar menu end-->
          </div>
      </aside>
      <!--sidebar end-->
      
      <!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->
      <!--main content start-->
      <section id="main-content" style="margin-left: 210px;">
          <section class="wrapper">
          <h3><i class="fa fa-angle-right"></i> Gráficos</h3>
              <!-- page start-->
              <div class="tab-pane" id="chartjs">
                  <div class="row mt">
                      <div class="col-lg-6">
                          <div class="content-panel">
							  <h4><i class="fa fa-angle-right"></i> Dona</h4>
                              <div class="panel-body text-center">
                                  
      <!-- **********************************************************************************************************************************************************
      AQUI ESTA LA DONA Y EL BOTON
      *********************************************************************************************************************************************************** -->
   
                                  <canvas id="dona" height="300" width="400" style="width: 400px; height: 300px;"></canvas>
									<p> Boton temporal para que se grafique</p>
									<button type="button" class="btn"
									onClick="dibujarDona('dona')">Mostrar dona</button>
	<!-- **********************************************************************************************************************************************************
      FIN LA DONA Y EL BOTON
      *********************************************************************************************************************************************************** -->
   								
                              </div>
                              <div class="panel-body text-center">
                                  
      <!-- **********************************************************************************************************************************************************
      AQUI ESTA LA DONA Y EL BOTON DUPLICADO 
      *********************************************************************************************************************************************************** -->
   
                                  <canvas id="dona_2" height="300" width="400" style="width: 400px; height: 300px;"></canvas>
									<p> Boton temporal para que se grafique</p>
									<button type="button" class="btn"
									onClick="dibujarDona('dona_2')">Mostrar dona</button>
	<!-- **********************************************************************************************************************************************************
      FIN LA DONA Y EL BOTON DUPLICADO 
      *********************************************************************************************************************************************************** -->
   								<!-- **********************************************************************************************************************************************************
      AQUI ESTA LA DONA  AJAX
      *********************************************************************************************************************************************************** -->
									<div id="dona_ajax">
										<canvas id="dona_3" height="300" width="400" style="width: 400px; height: 300px;"></canvas>
									</div>
	<!-- **********************************************************************************************************************************************************
      FIN LA DONA  AJAX
      *********************************************************************************************************************************************************** -->
   			
                              </div>
                          </div>
                      </div>
                      <div class="col-lg-6">
                          <div class="content-panel">
							  <h4><i class="fa fa-angle-right"></i>Línea</h4>
                              <div class="panel-body text-center">
                                  <canvas id="line" height="300" width="400" style="width: 400px; height: 300px;"></canvas>
                              </div>
                          </div>
                      </div>
                  </div>
                  <div class="row mt">
                      <div class="col-lg-6">
                          
                      </div>
                      <div class="col-lg-6">
                          
                      </div>
                  </div>
                  <div class="row mt">
                      <div class="col-lg-6">
                          <div class="content-panel">
							  <h4><i class="fa fa-angle-right"></i> Barrras</h4>
                              <div class="panel-body text-center">
                                  <canvas id="bar" height="300" width="400" style="width: 400px; height: 300px;"></canvas>
                              </div>
                          </div>
                      </div>
                      <div class="col-lg-6">
                          <div class="content-panel">
							  <h4><i class="fa fa-angle-right"></i> Torta</h4>
                              <div class="panel-body text-center">
                                  <canvas id="pie" height="300" width="400" style="width: 400px; height: 300px;"></canvas>
                              </div>
                          </div>
                      </div>
                  </div>
              </div>
              <!-- page end-->
          </section>          
      </section><!-- /MAIN CONTENT -->

      <!--main content end-->
      <!--footer start-->
      <footer class="site-footer">
          <div class="text-center">
              2018 - Argentina
              <a href="graficar.gsp#" class="go-top">
                  <i class="fa fa-angle-up"></i>
              </a>
          </div>
      </footer>
      <!--footer end-->
  </section>

    <!-- js placed at the end of the document so the pages load faster -->
    <asset:javascript src="tema_graficos/js/actualizadas/jquery-3.3.1.js"/>
    

    <!--common script for all pages-->
    <!--script for this page-->
     <asset:javascript src="tema_graficos/js/chart-master/Chart.js"/>
    
    <!-- Para cargar los datos de los gráficos-->
    <asset:javascript src=tema_graficos/js/chartjs-conf.js"/>
        <!--Libreria RGRAPH-->
    <!--Aprovechando funcionalidad simplificada AJAX para comunicacion-->
    <!--Tambien para graficos SVG-->
    <asset:javascript src="tema_graficos/js/RGraph/libraries/RGraph.svg.common.ajax.js"/>
    <asset:javascript src="tema_graficos/js/RGraph/libraries/RGraph.svg.common.core.js"/>
    <asset:javascript src="tema_graficos/funcionesJS/dibujar_Dona.js"/>
	<asset:javascript src="tema_graficos/funcionesJS/dibujar_Dona_AJAX.js"/>
	<asset:javascript src="tema_graficos/funcionesJS/recibir_JSON_AJAX.js"/>

	<!-- js placed at the end of the document so the pages load faster -->
    <script src="tema_graficos/js/actualizadas/jquery-3.3.1.js"></script>
    
    <!--common script for all pages-->
   
    <!--script for this page-->
    <script src="tema_graficos/js/chart-master/Chart.js"></script>
    <!-- Para cargar los datos de los gráficos-->
    <script src="tema_graficos/js/chartjs-conf.js"></script>
    <!--Libreria RGRAPH-->
    <!--Aprovechando funcionalidad simplificada AJAX para comunicacion-->
    <!--Tambien para graficos SVG-->
    <script src="tema_graficos/funcionesJS/utilidad_constantes_colores_meses.js"></script>
    <script src="tema_graficos/funcionesJS/dibujar_Dona.js"></script>
	<script src="tema_graficos/funcionesJS/dibujar_Dona_AJAX.js"></script>
	<script src="tema_graficos/funcionesJS/recibir_JSON_AJAX.js"></script>
    <script src="tema_graficos/js/RGraph/libraries/RGraph.svg.common.ajax.js"></script>
    <script src="tema_graficos/js/RGraph/libraries/RGraph.svg.common.core.js"></script>
    

</body>
</html>
