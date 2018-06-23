package fiuba.materia7510.aplicacion.generador

//import grails.rest.*

//@Resource(uri='/estadios')
class Estadio {
		
	String nombre
	String estadio
	def sennales = []
    static constraints = {
    		nombre 	blank: false, nullable: false, unique: true
			estadio	blank: false, nullable: false
			sennales nullable:true 
    }
    
    static mapping = {
    	 estadio type: "text"
    	
    /*after inserting a Blob or Clob:
    	 (text es un caso particular de clob), usar instancia.refresh()
    	 */
    }
    String toString(){
		nombre
    }
    
}
