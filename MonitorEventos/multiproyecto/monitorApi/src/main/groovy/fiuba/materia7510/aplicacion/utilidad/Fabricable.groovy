package fiuba.materia7510.aplicacion.utilidad

import fiuba.materia7510.aplicacion.proveedor.Estado
import fiuba.materia7510.aplicacion.proveedor.EstadoMock
import fiuba.materia7510.aplicacion.proveedor.Flujo
import fiuba.materia7510.aplicacion.proveedor.FlujoMock
import fiuba.materia7510.aplicacion.proveedor.Ticket
import fiuba.materia7510.aplicacion.proveedor.TicketMock
import fiuba.materia7510.aplicacion.generador.Regla
import fiuba.materia7510.aplicacion.generador.Estadio


interface Fabricable {
	
	
	Ticket	crearTicket(Estado estado, Flujo flujo, def codigo, String titulo, String descripcion, String propietario)
	
	Flujo 	crearFlujo(String nombre,String descripcion)
	
	
	Estadio crearEstadio(String estadio,String nombre)
	 	
	Regla 	crearRegla(String regla,String nombre)
	
	/******************************/
	
	EstadoMock crearEstadoMock(String estado, String descripcion)

	FlujoMock crearFlujoMock(String nombre, String descripcion)
		
	TicketMock 	crearTicketMock(EstadoMock estado, FlujoMock flujo, def codigo, String titulo, String descripcion, String propietario)
		 							
}
	

