package fiuba.materia7510.aplicacion.usuario

import fiuba.materia7510.aplicacion.usuario.Persona
import fiuba.materia7510.aplicacion.actividad.Actividad
import fiuba.materia7510.aplicacion.rol.Rol


class Usuario extends Persona{
	
	String autenticacionId
	
	String password
	
	Date dateCreated
	
	Rol rol
	
	Actividad actividad
	
	static constraints = {
			
			autenticacionId size: 3..20, unique: true, blank: false
			
			password size: 6..8, blank: false, validator: { passwd, usuario ->
			passwd != usuario.autenticacionId }
			
			rol()			
			actividad()
	}

}
