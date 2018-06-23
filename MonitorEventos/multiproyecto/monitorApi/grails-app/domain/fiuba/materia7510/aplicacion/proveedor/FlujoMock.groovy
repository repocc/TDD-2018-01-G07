package fiuba.materia7510.aplicacion.proveedor

import grails.rest.*
import fiuba.materia7510.aplicacion.proveedor.Dato
import fiuba.materia7510.aplicacion.proveedor.EstadoMock

@Resource(uri='/flujosmock')
class FlujoMock{

	String caracteristica = "REST-Funcionalidad."
    
    String nombre
    
    String descripcion
	
	Date	dateCreated
	
	static hasMany 		=	[ estados: 	EstadoMock]
	
	static constraints ={
		
		nombre 		blank:false, maxSize:50, unique:true
		descripcion blank:false, maxSize:240 , nullable:true
		
	}
    
	
	
	
}


