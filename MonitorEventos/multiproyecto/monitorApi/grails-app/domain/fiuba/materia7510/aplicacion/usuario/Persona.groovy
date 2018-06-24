package fiuba.materia7510.aplicacion.usuario

class Persona {
	
	String nombre
	String apellido
    static constraints = {
		nombre blank: false
		apellido blank:false
    }

	String toString(){
		"${apellido}, ${nombre}"
		}
}
