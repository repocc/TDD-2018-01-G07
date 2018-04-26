package tp7510.monitor


class DemoController {

	
    def index() {
     
    render "Soy el índice :-) "
    render "<h1> Bienvenidos dicen Fontana y Rodríguez.</h1>" 
    render "<h1> Prueba_UNO.namespace grails</h1>" 
    render  funcion_Prueba()
    render "<h1> Prueba_DOS.namespace grails.prueba_cero. En carpeta raiz</h1>" 
    render  funcion_Prueba_cero()
    render "<h1> Prueba_TRES.Instanciando Clase DEMO</h1>" 
    render  new Demo().demostracion()
    render "<h1> Prueba_CUATRO.Instanciando Clase DEMO LLamando a funciones en carpetas anidadas indirectamente.</h1>" 
    //render  new Demo().demo_prueba_subcarpeta()
    render "<h1> No funciona llamar a carpetas anidadas directamente.</h1>" 
    render "<h1> FIN</h1>" 
    render "<p>**********</p>"
    }
    
    
  def funcion_Prueba() {
	//estoy en el nampespace ns grails
		clj.soy_una_funcion_en_carpeta_raiz()
		
		}
		
	def funcion_Prueba_cero() {
		def rta = clj['grails.prueba_cero'].funcion_cero()
		rta
		}
		//no funciona directamente en carpetas anidadas
	def funcion_Prueba_dos() {
		def rta = clj['grails.gratitud.pruebaDos'].soy_una_funcion_en_carpeta_gratitud()
		rta
		}
		
	/*def funcion_Prueba_tres() {
		def rta = clj['grails.tp7510.monitor.gratitud.pruebatres'].soy_una_funcion_en_carpeta_gratitud()
		rta
		}
		*/
}
