package fiuba.materia7510.aplicacion

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification
import fiuba.materia7510.aplicacion.generador.ClojureProcesador
import fiuba.materia7510.aplicacion.generador.*

class MotorServiceSpec extends Specification implements ServiceUnitTest<MotorService>{

    def setup() {
    }

    def cleanup() {
    }

	void "test invacando Main personalizado"() {
        when:
        def ns  ="/home/comunity/Workspace/ClojureProyecto/privadoRepoTP/tpValidadorReglas/TDD-2018-01-G07/MonitorEventos/Multiproyecto/src/main/resources/fiuba/materia7510/aplicacion/generador/motor"
		
		println	( "El namespace es *********** ${ns}")
       
        def fn = null
        def ns_IFn 	= ns?		ClojureProcesador.cargarClojureNamespace (ns):null
			fn  	= ns_IFn?	ClojureProcesador.obtenerFuncion ("principal"):null
		def rta 	= fn?		ClojureProcesador.invocar(fn):null
		println """ATRAVESANDO Lost test")
		println (rta)
        then:
            true ==false
    }
    void "test procesador SUMA"() {
        when:
        def fn  = ClojureProcesador.obtenerFuncion ("+")
		Number v1 = 10
		Number v2 = 5
		def rta = ClojureProcesador.invocar(fn, v1, v2)
		
		println (rta)
        then:
            15 == rta
    }
}
