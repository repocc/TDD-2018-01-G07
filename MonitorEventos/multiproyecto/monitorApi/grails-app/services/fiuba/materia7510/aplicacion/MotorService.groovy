package fiuba.materia7510.aplicacion

import grails.gorm.transactions.Transactional
import fiuba.materia7510.aplicacion.generador.ClojureProcesador
import fiuba.materia7510.aplicacion.generador.*


@Transactional
class MotorService {
	
	
	static final def ns = "motor"
	static final def iniciar_fn			= "iniciar"	
	static final def procesar_datos_fn 	= "procesar_datos"
	static final def process_data_dropping_signals_fn = "process_data_dropping_signals"
	static final def procesar_datos_recuperar_sennales_fn ="procesar_datos_recuperar_sennales"
	static final def inicializarProcesarSennalizar_fn = "agregar_reglas_procesar_datos_emitir_sennales"
	static final def consultar_contador_fn				= "consultar_contador"
	static final def decodificar_JsonFormato_a_MapFormatoClojure_fn="decodificar_JsonFormato_a_MapFormatoClojure"
	static final def codificar_MapFormatoClojure_a_JsonFormato_fn="codificar_MapFormatoClojure_a_JsonFormato"
	static final def mapear_aplanar_resultados_del_Estado_fn="mapear_aplanar_resultados_del_Estado"
	
	
	static def cargar_Recursos_para_ejecucion (String ns,String funcion_invocada){
	 		
      def estado  = ClojureProcesador.requerir_libreria ("clojure.core","require", ns)
		
		//println "REQUERIR LIBRERIA*****************************${estado}"
		
	  def ns_IFn 	= ClojureProcesador.cargarClojureNamespace(ns)
		
		//println "NAMESPACE*********************************${ns_IFn}"
     
      def fn  		= ns_IFn?	ClojureProcesador.obtenerFuncion (ns_IFn,funcion_invocada):null
		
		//println "FUNCION INVOCADA*********************************${fn}"
	  return fn		
	}
	static def inicializar_procesador (def rules){
		
		ClojureProcesador.invocar (cargar_Recursos_para_ejecucion(ns,iniciar_fn), rules)  
	}
		
	static def procesar_datos (def state,def dato){
		ClojureProcesador.invocar (cargar_Recursos_para_ejecucion(ns,procesar_datos_fn), state, dato)
	}

	static def process_data_dropping_signals(def state, def dato){
		
		ClojureProcesador.invocar (cargar_Recursos_para_ejecucion(ns,process_data_dropping_signals_fn), state, dato)

	}
	
	static def procesar_datos_recuperar_sennales(def state, def dato){
		ClojureProcesador.invocar (cargar_Recursos_para_ejecucion(ns,procesar_datos_recuperar_sennales_fn), state, dato)

	}
	
	static def consultar_contador(def state, def nombre_Contador, def argumento){
		ClojureProcesador.invocar (cargar_Recursos_para_ejecucion(ns,consultar_contador_fn), state, nombre_Contador, argumento)

	}
	
	static def decodificar_JsonFormato_a_MapFormatoClojure (def datoJSON, def keyFormato){
		
		ClojureProcesador.invocar (cargar_Recursos_para_ejecucion(ns, decodificar_JsonFormato_a_MapFormatoClojure_fn), datoJSON, keyFormato)

		}
	
	
	static def codificar_MapFormatoClojure_a_JsonFormato (def datoMapFormatoClojure){
		ClojureProcesador.invocar (cargar_Recursos_para_ejecucion(ns, codificar_MapFormatoClojure_a_JsonFormato_fn), datoMapFormatoClojure)

		}
	
	static def mapear_aplanar_resultados_del_Estado (def estado, def clave){
		
		
		ClojureProcesador.invocar (cargar_Recursos_para_ejecucion(ns, mapear_aplanar_resultados_del_Estado_fn), estado, clave)

		}	
 
}	
	
	

