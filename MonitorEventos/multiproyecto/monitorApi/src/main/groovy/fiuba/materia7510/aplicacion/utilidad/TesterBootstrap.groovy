package fiuba.materia7510.aplicacion.utilidad

import fiuba.materia7510.aplicacion.proveedor.Estado
import fiuba.materia7510.aplicacion.proveedor.Flujo
import fiuba.materia7510.aplicacion.proveedor.Ticket
import fiuba.materia7510.aplicacion.proveedor.TicketMock
import fiuba.materia7510.aplicacion.generador.Regla
import fiuba.materia7510.aplicacion.generador.Estadio
import fiuba.materia7510.aplicacion.MotorService

enum Colores{ VERDE("VERDE_COLOR"), AMARILLO("AMARILLO_COLOR"), ROJO("ROJO_COLOR")
	String nombre
	Colores (String nom){
		nombre = nom}

	String getColor(){
		nombre
	}
}
//Colores.values()
//Colores​.VERDE.getColor()​

 
		
		
enum Alimentos{ PASTAS("PASTAS_COMIDA"), CARNES("CARNES_COMIDA"), VEGETALES("VEGETALES_COMIDA")
		String nombre
		Alimentos (String nom){
		nombre = nom}

		String getAlimento(){
		nombre
		}
}
 

class TesterBootstrap {
/*EJEMPLO ESTRUCTURA ESTADO REGLAMENTO FULL
 * * {:Contadores {:Ticket-Contador [[] true [0]], :Ticket-Contador-Propietario [[(current "propietario")] true {}], :Ticket-Contador-Rojo [[] (starts-with? (current "descripcion") "R") [0]], :Ticket-Contador-Titulo [[current "titulo"] true {}], :email-count [[] true [0]], :spam-count [[] (current "spam") [0]], :spam-important-table [[(current "spam") (current "important")] true {}]}, :ContadorSteps {}, :Sennales {:Ticket-fraction-Rojo [(/ (counter-value "Ticket-Contador-Rojo" []) (counter-value "Ticket-Contador" [])) nil], :spam-fraction [(/ (counter-value "spam-count" []) (counter-value "email-count" [])) true]}, :DatosPasados {}}))
*/

 def cargar_Datos(){
			
	/***Creacion de regla y se inicializa motor-procesador***/
	def reglamento_1 = '((define-counter "email-count" [] true)(define-counter "spam-count" [] (current "spam")) (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])(counter-value "email-count" []))}true)(define-counter "spam-important-table" [(current "spam")(current "important")]true))'
						
	def reglamento_2= '((define-counter "Ticket-Contador" [] true)(define-counter "Ticket-Contador-Titulo" [current "titulo"] true) (define-counter "Ticket-Contador-Rojo" [] (starts-with? (current "descripcion") "R"))(define-counter "Ticket-Contador-Propietario" [(current "propietario")] true) (define-signal {"Ticket-fraction-Rojo" (/ (counter-value "Ticket-Contador-Rojo" [])(counter-value "Ticket-Contador" []))}))'
	
	def reglamento_full = '((define-counter "email-count" [] true)(define-counter "spam-count" [] (current "spam")) (define-signal {"spam-fraction" (/ (counter-value "spam-count" [])(counter-value "email-count" []))}true) (define-counter "spam-important-table" [(current "spam")(current "important")]true) (define-counter "Ticket-Contador" [] true) (define-counter "Ticket-Contador-Titulo" [current "titulo"] true) (define-counter "Ticket-Contador-Rojo" [] (starts-with? (current "descripcion") "R"))(define-counter "Ticket-Contador-Propietario" [(current "propietario")] true) (define-signal {"Ticket-fraction-Rojo" (/ (counter-value "Ticket-Contador-Rojo" [])(counter-value "Ticket-Contador" []))}true))'
	
	new Regla (regla: reglamento_full, nombre:"Full").save(failOnError:true, flush:true).refresh()
	
	new Regla (regla: reglamento_1,	nombre:"Primero").save(failOnError:true, flush:true).refresh()
	
	
	new Regla (regla: reglamento_2,	nombre:"Segundo").save(failOnError:true, flush:true).refresh()
		
		//inicializa procesador
	
	def estad = MotorService.inicializar_procesador(reglamento_full)
	
	println "Estado inicializar procesador STATUS:... ${estad}"
	
	new Estadio (estadio: estad, nombre:"Primero").save(failOnError:true, flush:true).refresh()	
		
		
	println "Estado inicializar procesador STATUS:... ${estad}"
		
		
	String tit1	= 	'email-count'
	String tit2	=	'spam-count'
	/**********FIN CREACION REGLAS ***************/
	def f =null
	def e1 = new Estado (estado:Colores.VERDE.getColor()).save(failOnError:true, flush:true)
	def e2 = new Estado (estado:Colores.AMARILLO.getColor()).save(failOnError:true, flush:true)
	def e3 = new Estado (estado:Colores.ROJO.getColor()).save(failOnError:true, flush:true)
	def e4 = new Estado (estado:Alimentos.PASTAS.getAlimento()).save(failOnError:true, flush:true)
	def e5 = new Estado (estado:Alimentos.CARNES.getAlimento()).save(failOnError:true, flush:true)
	def e6 = new Estado (estado:Alimentos.VEGETALES.getAlimento()).save(failOnError:true, flush:true)
	
	f =	new Flujo()
	
	f?.addToEstados (e1)
	
	f?.addToEstados (e2)
	
	f?.addToEstados (e3)
	
	f?.save(failOnError:true, flush:true)
	
	new TicketMock(estado:e1, flujo:f, codigo:1, titulo:tit1, descripcion:"MockA", propietario:"AA").save(failOnError:true, flush:true)
	
	
	new TicketMock(estado:e2, flujo:f,codigo:2, titulo:tit1, descripcion:"MockB", propietario:"BB").save(failOnError:true, flush:true)
	
	 
	new TicketMock(estado:e3,flujo:f, codigo:3, titulo:tit1, descripcion:"MockC", propietario:"CC").save(failOnError:true, flush:true)
	
	f =	new Flujo()
	
	f?.addToEstados (e4)
	
	f?.addToEstados (e5)
	
	f?.addToEstados (e6)
	
	f?.save(failOnError:true, flush:true)
		
		
	new TicketMock(estado:e4, flujo:f, codigo:4, titulo: tit2, descripcion:"MockD", propietario:"DD").save(failOnError:true, flush:true)
        
		 
	new TicketMock(estado:e5, flujo:f, codigo:5, titulo: tit2, descripcion:"MockE", propietario:"EE").save(failOnError:true, flush:true)
        
         
	new TicketMock(estado:e6, flujo:f, codigo:6, titulo: tit2, descripcion:"MockF", propietario:"FF").save(failOnError:true, flush:true)
        
    /*******************************************/
   
    
    new TicketMock(estado:Colores.ROJO.getColor(), flujo:f, codigo:12345, titulo: "AUGUSTO", descripcion:"EMISOR: AUGUSTO RECEPTOR: Arevalo", propietario:"Propiedad de Arevalo").save(failOnError:true, flush:true)
     
    new TicketMock(estado:Colores.VERDE.getColor(), flujo:f, codigo:54321, titulo: "AUGUSTO", descripcion:"RETADOR: AUGUSTO SUSODICHO: Arevalo", propietario:"Propiedad de Arevalo").save(failOnError:true, flush:true)
    
	new TicketMock(estado:Colores.VERDE.getColor(), flujo:f, codigo:111, titulo: "Euriterco", descripcion:"RETADOR: AUGUSTO SUSODICHO: Arevalo", propietario:"Propiedad de Arevalo").save(failOnError:true, flush:true)
    
    new TicketMock(estado:Colores.VERDE.getColor(), flujo:f, codigo:111, titulo: "Rascondio", descripcion:"RIVAL: Rascondio", propietario:"Rascondio").save(failOnError:true, flush:true)
    
    new TicketMock(estado:Colores.AMARILLO.getColor(), flujo:f, codigo:1111, titulo: "Rasputin", descripcion:"Retorno a produccion", propietario:"Rosalinda").save(failOnError:true, flush:true)
    
	
	
	
	}


}//fin clase
