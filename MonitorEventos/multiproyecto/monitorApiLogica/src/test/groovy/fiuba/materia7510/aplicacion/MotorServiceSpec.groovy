package fiuba.materia7510.aplicacion

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification
import clojure.java.api.Clojure
import clojure.lang.IFn
import fiuba.materia7510.aplicacion.generador.ClojureProcesador
import fiuba.materia7510.aplicacion.MotorService


class MotorServiceSpec extends Specification implements ServiceUnitTest<MotorService>{

    def setup() {
		
    }

    def cleanup() {
    }
    
    void "test SERVICIO MOTOR deftest unconditional-counter-test [ rules datos]	"() {
        when:
        def reglas            = '((define-counter "email-count" [] true)(define-counter "spam-count" [] (current "spam")) (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])(counter-value "email-count" []))}true)(define-counter "spam-important-table" [(current "spam")(current "important")]true))'
		def nombreContador 	= 'email-count'
        def dato 			= '{"spam" true}'
        def arg		= '[]'
        /**
         * (deftest unconditional-counter-test
			(let [st0 (initialize-processor rules)
        st1 (process-data-dropping-signals st0 {"spam" true})
        st2 (process-data-dropping-signals st1 {"spam" true})]
			(is (= 2
           (query-counter st2 "email-count" [])))))
         * 
         * 
         *  static def inicializar_procesador (def rules){
			static def procesar_datos (def state,def dato){
			static def process_data_dropping_signals(def state, def dato){
			static def consultar_contador(def state, def nombre_Contador, def argumento){
         * */
		
		def ns = "motor"
		def iniciar_fn			= "iniciar"	
		def procesar_datos_fn 	= "procesar_datos"
		def process_data_dropping_signals_fn = "process_data_dropping_signals"
		def inicializarProcesarSennalizar_fn = "agregar_reglas_procesar_datos_emitir_sennales"
		def consultar_contador_fn				= "consultar_contador"
		/*
		String ini = 		MotorService.cargar_Recursos_para_ejecucion (ns,iniciar_fn)
		println "TEST_FUNCION INVOCADA: ${ini}"
		def process =	MotorService.cargar_Recursos_para_ejecucion (ns,process_data_dropping_signals_fn)
		println "TEST_FUNCION INVOCADA: ${process}"
		def consQ	= 	MotorService.cargar_Recursos_para_ejecucion (ns,consultar_contador_fn)
		println "TEST_FUNCION INVOCADA:  ${consQ}"
		*/
		
        def st0 = MotorService.inicializar_procesador (reglas)?:null
        
        def st1 = MotorService.process_data_dropping_signals( st0 ,dato)?:null
        
        def st2	= MotorService.process_data_dropping_signals( st1, dato)?:null
		
		def rta = MotorService.consultar_contador( st2, nombreContador,arg)?:null
	
		println "SALIDA DEL PROCESO:"
		
		println "st0 ${st0}"
		println "st1 ${st1}"
		println "st2 ${st2}"
		println "rta ${rta}"
		println "******************"
        then:
            2 == rta
    }
    
  	void "test invocando agregar_reglas_procesar_datos_emitir_sennales [ rules datos]	"() {
        when:
        def reglas            = '((define-counter "email-count" [] true)(define-counter "spam-count" [] (current "spam")) (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])(counter-value "email-count" []))}true)(define-counter "spam-important-table" [(current "spam")(current "important")]true))'
		def nombreContador 	= 'email-count'
        def dato 			= '{"spam" true}'
        
        
        def ns = "motor"
		println	 "El namespace es  ${ns}"
		def funcion_invocada = "agregar_reglas_procesar_datos_emitir_sennales"		
        def fn = null
			
        def estado = ClojureProcesador.requerir_libreria ("clojure.core","require", ns)
		println "LIBRERIA CARGADA_TEST AGREGAR_REGLAS**${estado}+++++++++++++++++++++++++"
        def ns_IFn 	= ClojureProcesador.cargarClojureNamespace(ns)
        println ns_IFn
		fn  		= ns_IFn?	ClojureProcesador.obtenerFuncion (ns_IFn,funcion_invocada):null
		println fn
		def rta 	= fn?		ClojureProcesador.invocar(fn,reglas,dato):null
		println "SALIDA DEL PROCESO:"
		println rta
		println "******************"
        then:
            true ==true
    }
     
	
    void "test invocando procesarDatos_ModoBatch [rules nombreContador datos]	"() {
        when:
        def reglas            = '((define-counter "email-count" [] true)(define-counter "spam-count" [] (current "spam")) (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])(counter-value "email-count" []))}true)(define-counter "spam-important-table" [(current "spam")(current "important")]true))'
		def nombreContador 	= 'email-count'
        def dato 			= '{"spam" true}'
        
        
        def ns = "motor"
		println	 "El namespace es  ${ns}"
		def funcion_invocada = "procesarDatos_ModoBatch"		
        def fn = null
			
        def estado = ClojureProcesador.requerir_libreria ("clojure.core","require", ns)
	
        def ns_IFn 	= ClojureProcesador.cargarClojureNamespace(ns)
        println ns_IFn
		fn  		= ns_IFn?	ClojureProcesador.obtenerFuncion (ns_IFn,funcion_invocada):null
		println fn
		def rta 	= fn?		ClojureProcesador.invocar(fn,reglas, nombreContador,dato):null
		println "SALIDA DEL PROCESO:"
		println rta
		println "******************"
        then:
            true ==true
    }
    
	void "test invocando iniciar_test rules"(){
        when:
        def reglas			= '((define-counter "email-count" [] true)(define-counter "spam-count" [] (current "spam")) (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])(counter-value "email-count" []))}true)(define-counter "spam-important-table" [(current "spam")(current "important")]true))'
        def nombreContador 	= 'email-count'
        def dato 			= '{"spam" true}'
        
        
        def ns = "motor"
		println	 "El namespace es  ${ns}"
		def funcion_invocada = "iniciar"
        
        def fn = null
        
        def estado = ClojureProcesador.requerir_libreria ("clojure.core","require", ns)

        def ns_IFn 	= ClojureProcesador.cargarClojureNamespace(ns)
        println ns_IFn
		fn  		= ns_IFn?	ClojureProcesador.obtenerFuncion (ns_IFn,funcion_invocada):null
		println fn
		def rta 	= fn?		ClojureProcesador.invocar(fn,reglas):null
		println "SALIDA DEL PROCESO:"
		println rta
		println "******************"
        then:
            true ==true 
    }
	
	
	
	void "test invocando saludar"() {
        when:
        def ns = "motor"
		println	( "El namespace es *********** ${ns}")
		def funcion_invocada = "saludar"
        def fn = null
        
        def estado = ClojureProcesador.requerir_libreria ("clojure.core","require", ns)
        
        def ns_IFn 	= ClojureProcesador.cargarClojureNamespace(ns)
        println ns_IFn
		fn  		= ns_IFn?	ClojureProcesador.obtenerFuncion (ns_IFn,funcion_invocada):null
		println fn
		def rta 	= fn?		ClojureProcesador.invocar(fn):null
		println "SALIDA DEL PROCESO:"
		println rta
		println "******************"
        then:
            true ==true
    }
    
	void "test invocando Main personalizado"() {
        when:
        def reglas            = '((define-counter "email-count" [] true)(define-counter "spam-count" [] (current "spam")) (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])(counter-value "email-count" []))}true)(define-counter "spam-important-table" [(current "spam")(current "important")]true))'
		def nombreContador 	= 'email-count'
        def dato 			= '{"spam" true}'
        def ns = "motor"
		println	( "El namespace es *********** ${ns}")
		def funcion_invocada = "principal"
        def fn = null
        
        def estado = ClojureProcesador.requerir_libreria ("clojure.core","require", ns)

        def ns_IFn 	= ClojureProcesador.cargarClojureNamespace (ns)
		fn		  	= ns_IFn?	ClojureProcesador.obtenerFuncion (ns_IFn, funcion_invocada):null
		def rta 	= fn? 		ClojureProcesador.invocar(fn, reglas, nombreContador,dato):null
		println "SALIDA DEL PROCESO:"
		println rta
		println "******************"
        then:
            true == true
    }
    
    void "test procesador SUMA"() {
        when:
        def fn  = ClojureProcesador.obtenerFuncionEstandard ("+")
		Number v1 = 10
		Number v2 = 5
		def rta = ClojureProcesador.invocar(fn, v1, v2)
		println "SALIDA DEL PROCESO SUMA"
		println (rta)
        then:
            15 == rta
    }
}
