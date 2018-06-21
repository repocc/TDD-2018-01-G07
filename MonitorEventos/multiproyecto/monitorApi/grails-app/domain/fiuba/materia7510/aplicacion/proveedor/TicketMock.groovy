package fiuba.materia7510.aplicacion.proveedor

import grails.rest.*

import fiuba.materia7510.aplicacion.proveedor.FlujoMock
import fiuba.materia7510.aplicacion.proveedor.EstadoMock

@Resource(uri='/ticketsmock')
class TicketMock {

	String caracteristica = "REST-Funcionalidad."
    EstadoMock 	estado
	
	Long 	codigo
	
	String 		titulo
	String 		propietario
	String 		descripcion

	Date		dateCreated
	Date		lastUpdated
	
	
	 /* Ejemplo
	  * If the foreign key exists on the address table,
	  *  then that address can only have one person.
	  *  If the foreign key is on the person table,
	  *  multiple persons can have the same address.
	  * 
		*/
	FlujoMock flujo
    
    static constraints = {
		
		
		titulo 			maxSize: 50 ,	blank: false, nullable: false
		propietario		maxSize: 50, 	blank: false, nullable: false
        descripcion 	maxSize: 140 ,	blank: false, nullable: false
        estado() 			
        flujo()				
        codigo 			unique:true
    }
    String toString(){ "Ticket de $propietario " }
    
}
