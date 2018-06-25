package fiuba.materia7510.aplicacion.actividad

enum Actividad{ 
	
	DISTRIBUIDOR("Usuario distribuidor de tickets"), CONSULTOR("Usuario consultor-generador de reglas")
	
	String nombre
	
	Actividad (String nom){
		nombre = nom}

	String getActividad(){
		nombre
	}
}
