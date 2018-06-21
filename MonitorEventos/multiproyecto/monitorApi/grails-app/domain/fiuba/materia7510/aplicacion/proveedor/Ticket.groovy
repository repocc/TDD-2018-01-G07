package fiuba.materia7510.aplicacion.proveedor


import fiuba.materia7510.aplicacion.proveedor.Dato
import fiuba.materia7510.aplicacion.proveedor.Flujo
import fiuba.materia7510.aplicacion.proveedor.Estado


class Ticket extends Dato {

	
	Estado 	estado //estado actual
	
	String 		codigo 
	
	String 		titulo
	String 		propietario
	String 		descripcion

		
	 /* Ejemplo
	  * If the foreign key exists on the address table,
	  *  then that address can only have one person.
	  *  If the foreign key is on the person table,
	  *  multiple persons can have the same address.
	  * 
		*/
	Flujo flujo
    
    static constraints = {
		
		
		titulo 			maxSize: 50,	blank: false
		propietario		maxSize: 50, 	blank: false
        descripcion 	maxSize: 140,	blank: false
        codigo 			unique:true
        flujo()			
        estado() 
    }
    String toString(){ "Ticket de $propietario " }
    
	def validarEstadoPerteneceFlujo() {
		 flujo?.estados.find{ it.estado == estado.estado }
	}
}
