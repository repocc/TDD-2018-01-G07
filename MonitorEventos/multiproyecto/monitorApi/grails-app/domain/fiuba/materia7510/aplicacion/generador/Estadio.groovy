package fiuba.materia7510.aplicacion.generador

import grails.rest.*

@Resource(uri='/estadios')
class Estadio {
	
	String estadio
	
    static constraints = {
		estadio	blank: false, nullable: false 
    }
}
