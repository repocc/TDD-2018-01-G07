package fiuba.materia7510.aplicacion.autenticacion

import fiuba.materia7510.aplicacion.usuario.Usuario

class Autenticador {
	//log de inicios y finalizaciones de sesion de usuarios
	String nombre
	
	Usuario usuario
	
	Date dateCreated
	
	String toString(){
		
		"[${nombre}] [Usuario] [${usuario?.toString()}] [${dateCreated}]."
	}
	
	static constraints ={
		
		nombre inList:["INICIO", "CIERRE"]
		
		usuario()
		
		}
}
