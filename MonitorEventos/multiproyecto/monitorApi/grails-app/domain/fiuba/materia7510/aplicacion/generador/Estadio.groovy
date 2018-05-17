package fiuba.materia7510.aplicacion.generador

import grails.rest.*

@Resource(uri='/estadios')
class Estadio {
		//temporal
	static String estatus = null
	String estadio
	
    static constraints = {
		estadio	blank: false, nullable: false 
    }
}
