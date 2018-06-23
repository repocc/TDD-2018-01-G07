package fiuba.materia7510.aplicacion.generador

//import grails.rest.*

//@Resource(uri='/reglas')
class Regla {
	
	String nombre
	String regla
	
    static constraints = {
    		nombre 	blank: false, nullable: false, unique: true
			regla	blank: false, nullable: false 
    }
    
    static mapping = {
    	 regla type: "text"
    	 
    /*after inserting a Blob or Clob:
    	 (text es un caso particular de clob), usar instancia.refresh()
    	 */
    }
    String toString(){
			nombre
	}
}
