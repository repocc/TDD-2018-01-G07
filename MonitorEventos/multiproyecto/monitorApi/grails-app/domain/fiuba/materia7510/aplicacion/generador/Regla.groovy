package fiuba.materia7510.aplicacion.generador

import grails.rest.*

@Resource(uri='/reglas')
class Regla {
	
	static String reglamento = null
	String regla
	
    static constraints = {
		reglas	blank: false, nullable: false 
    }
}
