package fiuba.materia7510.aplicacion.proveedor

import grails.rest.*



@Resource(uri='/estadosmock')
class EstadoMock {
	
	String caracteristica ="REST-funcionalidad."
	/*Empleado para TesterBootstrap*/
	/*Para modelo de pruebas de transferencia*/
	/*por la web*/
	String estado
	String descripcion
	Date	dateCreated
	Date	lastUpdated
	
    /**
     * NOTA Personal:This property blank is only applicable for a String field.*
     * 
     * blank:true means the field accepts an empty string or one composed only by spaces as valid values. Eg: "", "  "
     * nullable:true means the field accepts null as valid value.
     * */
    static constraints = {
		
		estado	minSize:2,	maxSize:50, blank: false, nullable: false ,unique:true
		
		descripcion nullable:true , maxSize:240 ,blank: true
		
    }

	String toString(){
		estado
    }
}
