package fiuba.materia7510.aplicacion.generador

import grails.rest.*

@Resource(uri='/reglas')
class Regla {
	
	String reglas
	
    static constraints = {
		reglas	blank: false, nullable: false 
    }
}
