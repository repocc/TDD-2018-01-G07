package fiuba.materia7510.aplicacion.proveedor


import fiuba.materia7510.aplicacion.proveedor.Trayectoria

class Flujo {

	/*
	 * Many-to-one/one-to-one: 
	 * saves and deletes cascade from the owner	to the dependant
	 *  (the class with the belongsTo)
	 * One-to-many: saves always cascade from the one side
	 *  to the many side, but if the many side has belongsTo,
	 *  then deletes also cascade in that direction..
	 * */
	String nombre 
	
	String descripcion
	
	
	
	static hasMany 		=	[ estados: 	Estado]
	
	static constraints ={
		
		nombre 		blank:false, maxSize:50, unique:true
		descripcion blank:false, maxSize:240 , nullable:true
		
	}
	
	String toString(){
		nombre
	}
}


