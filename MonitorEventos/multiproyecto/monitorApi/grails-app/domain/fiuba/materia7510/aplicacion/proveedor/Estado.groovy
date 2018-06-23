package fiuba.materia7510.aplicacion.proveedor


import fiuba.materia7510.aplicacion.proveedor.Flujo


//import grails.rest.*


//@Resource(uri='/estados')
class Estado {
	
	
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
