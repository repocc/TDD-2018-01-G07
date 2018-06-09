package fiuba.materia7510.aplicacion.proveedor

import grails.rest.*

@Resource(uri='/flujos')
class Flujo {

	/*
	 * Many-to-one/one-to-one: 
	 * saves and deletes cascade from the owner	to the dependant
	 *  (the class with the belongsTo)
	 * One-to-many: saves always cascade from the one side
	 *  to the many side, but if the many side has belongsTo,
	 *  then deletes also cascade in that direction..
	 * */
	Date	dateCreated
	static hasMany 		=	[ estados: 	Estado]
	
    static mapping = {
    autoTimestamp true //por default es true
	estados cascade: 'all-delete-orphan'
	}
}


