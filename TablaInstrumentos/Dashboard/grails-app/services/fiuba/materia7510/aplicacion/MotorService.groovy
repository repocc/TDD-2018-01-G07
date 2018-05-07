package fiuba.materia7510.aplicacion

import grails.gorm.transactions.Transactional
import fiuba.materia7510.aplicacion.generador.ClojureProcesador
import fiuba.materia7510.aplicacion.generador.*


@Transactional
class MotorService {
	
	static final def ns  ="file:///home/comunity/Workspace/ClojureProyecto/privadoRepoTP/tpValidadorReglas/TDD-2018-01-G07/MonitorEventos/Multiproyecto/src/main/resources/fiuba/materia7510/aplicacion/generador/motor.clj"
	static final def iniciar_fn			= "iniciar"	
	static final def procesar_datos_fn 	= "procesar_datos"
	static final def process_data_dropping_signals_fn = "process_data_dropping_signals"
	static final def inicializarProcesarSennalizar_fn = "agregar_reglas_procesar_datos_emitir_sennales"
	
	
	static def inicializar_procesador (def rules){
		ClojureProcesador.invocar (ns ,iniciar_fn, rules)
    
	}
		
	static def procesar_datos (def state,def dato){
		ClojureProcesador.invocar (ns ,procesar_datos_fn, state, dato)
	}

	static def process_data_dropping_signals(def state, def dato){
		ClojureProcesador.invocar (ns ,process_data_dropping_signals_fn, state, dato)

	}
}	
	
	

