package fiuba.materia7510.aplicacion.proveedor

import grails.rest.*

@Resource(uri='/estados')
class Estado {
	
	
	String estado
	
	Date	dateCreated
	Date	lastUpdated
	
    static constraints = {
		estado	minSize:2,	maxSize:20, blank: false, nullable: false
    }
}
