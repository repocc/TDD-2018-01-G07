package fiuba.materia7510.aplicacion.proveedor


class Ticket {

	
	Estado 	estado
	
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
	Flujo flujo
    
    static constraints = {
		estado 			blank: false, nullable: false 
		
		codigo 			unique:true
		
		propietario		maxSize: 50, 	blank: false, nullable: false
		titulo 			maxSize: 50 ,	blank: false, nullable: false
        descripcion 	maxSize: 140 ,	blank: false, nullable: false
    }
}
