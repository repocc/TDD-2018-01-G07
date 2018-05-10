package fiuba.materia7510.aplicacion

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification
import clojure.java.api.Clojure
import clojure.lang.IFn
import fiuba.materia7510.aplicacion.generador.ClojureProcesador
//import fiuba.materia7510.aplicacion.generador.*


class MotorServiceSpec extends Specification implements ServiceUnitTest<MotorService>{

    def setup() {
		
    }

    def cleanup() {
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
