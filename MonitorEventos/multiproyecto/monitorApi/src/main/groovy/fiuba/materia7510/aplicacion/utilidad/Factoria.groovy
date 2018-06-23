package fiuba.materia7510.aplicacion.utilidad

import fiuba.materia7510.aplicacion.proveedor.Estado
import fiuba.materia7510.aplicacion.proveedor.EstadoMock
import fiuba.materia7510.aplicacion.proveedor.Flujo
import fiuba.materia7510.aplicacion.proveedor.FlujoMock
import fiuba.materia7510.aplicacion.proveedor.Ticket
import fiuba.materia7510.aplicacion.proveedor.TicketMock
import fiuba.materia7510.aplicacion.generador.Regla
import fiuba.materia7510.aplicacion.generador.Estadio

/***********************************************************/
/** INFORMACION UTIL SOBRE SINGLETON EN GRAILS
 * 
 * // Use @Singleton to create a valid singleton class.
 * // We can also use @Singleton(lazy=true) for a lazy loading.
 * // singleton class.
 * @Singleton
 class Util {
    int count(text) {
        text.size()
    }
}
 
assert 6 == Util.instance.count("mrhaki")
 
try {
    new Util()
} catch (e) {
    assert e instanceof RuntimeException
    assert "Can't instantiate singleton com.mrhaki.blog.Util. Use com.mrhaki.blog.Util.instance" == e.message
}
* 
 * */

@Singleton
class Factoria implements Fabricable {

	Ticket crearTicket(Estado estado, Flujo flujo, def codigo, String titulo, String descripcion, String propietario){
		new Ticket(estado:estado, flujo:flujo, codigo:codigo, titulo: titulo, descripcion:descripcion, propietario:propietario)
	}
	
	Estado crearEstado(String estado,String descripcion){
		new Estado(estado:estado,descripcion:descripcion)
	}
	
	Flujo crearFlujo(String nombre, String descripcion){
		new Flujo(nombre:nombre,descripcion:descripcion)
	}
	
	Estadio crearEstadio(String estadio,String nombre){

		new Estadio(estadio:estadio,nombre:nombre)
	}	 
	
	Regla crearRegla(String regla,String nombre){
	 
		new Regla(regla:regla,nombre:nombre) 
	}

	/*******************************************/
	
	FlujoMock crearFlujoMock(String nombre, String descripcion){
		new FlujoMock(nombre:nombre,descripcion:descripcion)
	}
	
	EstadoMock crearEstadoMock(String estado,String descripcion){
		new EstadoMock(estado:estado, descripcion:descripcion)
	}	

	TicketMock crearTicketMock(EstadoMock estado, FlujoMock flujo, def codigo, String titulo, String descripcion, String propietario){
		new TicketMock(estado:estado, flujo:flujo, codigo:codigo, titulo: titulo, descripcion:descripcion, propietario:propietario)
	}
	

}
	

