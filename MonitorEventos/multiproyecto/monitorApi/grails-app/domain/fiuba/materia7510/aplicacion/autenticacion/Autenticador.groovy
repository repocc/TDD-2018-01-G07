package fiuba.materia7510.aplicacion.autenticacion

import fiuba.materia7510.aplicacion.usuario.Usuario

class Autenticador {
	//log de inicios y finalizaciones de sesion de usuarios
	String nombre
	
	Usuario usuario
	
	Date fecha_session = new Date() //visualizacion en listado cierre inicio sesión de usuarios:Tema scaffold.
	
	Date dateCreated //registro en base de datos
	
	String toString(){
		
		"[${nombre}] [Usuario] [${usuario?.toString()}] [${dateCreated}]."
	}
	
	static constraints ={
		
		nombre inList:["INICIO", "CIERRE"]
		
		fecha_session nullable:true
		
		usuario()
		
		}
}
