package monitorapi

import fiuba.materia7510.aplicacion.proveedor.*

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
 
class BootStrap {

    
    
    def init = { servletContext ->
		
		
		
		
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
		
		new TicketMock(estado:e1, flujo:f, codigo:1, titulo:"A", descripcion:"MockA", propietario:"AA").save(failOnError:true, flush:true)
        
		
		new TicketMock(estado:e2, flujo:f,codigo:2, titulo:"B", descripcion:"MockB", propietario:"BB").save(failOnError:true, flush:true)
        
         
		new TicketMock(estado:e3,flujo:f, codigo:3, titulo:"C", descripcion:"MockC", propietario:"CC").save(failOnError:true, flush:true)
        
        f =	new Flujo()
        
		f?.addToEstados (e4)
		
		f?.addToEstados (e5)
		
		f?.addToEstados (e6)
		
		f?.save(failOnError:true, flush:true)
			
		
		new TicketMock(estado:e4, flujo:f, codigo:4, titulo:"D", descripcion:"MockD", propietario:"DD").save(failOnError:true, flush:true)
        
		 
		new TicketMock(estado:e5, flujo:f, codigo:5, titulo:"E", descripcion:"MockE", propietario:"EE").save(failOnError:true, flush:true)
        
         
		new TicketMock(estado:e6, flujo:f, codigo:6, titulo:"F", descripcion:"MockF", propietario:"FF").save(failOnError:true, flush:true)
        
    }
    def destroy = {
    }
}

