package fiuba.materia7510.aplicacion.rol

enum Rol{ 
	
	ADMINISTRADOR("Usuario administrador"), CLIENTE("Usuario cliente")
	
	String nombre
	
	Rol (String nom){
		nombre = nom}

	String getRol(){
		nombre
	}
}
