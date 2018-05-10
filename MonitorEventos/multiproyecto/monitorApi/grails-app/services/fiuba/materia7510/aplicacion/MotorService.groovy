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
	static final def inicializarProcesarSennalizar_fn = "agregar_reglas_procesar_datos_emitir_sennales"
	
	
	
	static def cargar_Recursos_para_ejecucion (String ns,String funcion_invocada){
	 		
     final def estado  = ClojureProcesador.requerir_libreria ("clojure.core","require", ns)
	
     final def ns_IFn 	= ClojureProcesador.cargarClojureNamespace(ns)
     
     final def fn  		= ns_IFn?	ClojureProcesador.obtenerFuncion (ns_IFn,funcion_invocada):null
		
	 fn		
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
}	
	
	

