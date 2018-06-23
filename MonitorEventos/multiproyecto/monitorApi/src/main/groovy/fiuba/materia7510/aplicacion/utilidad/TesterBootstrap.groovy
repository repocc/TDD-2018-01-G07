package fiuba.materia7510.aplicacion.utilidad

import grails.converters.JSON

import fiuba.materia7510.aplicacion.utilidad.Factoria
import fiuba.materia7510.aplicacion.utilidad.Constantes
import fiuba.materia7510.aplicacion.utilidad.Impresor
import fiuba.materia7510.aplicacion.proveedor.Estado
import fiuba.materia7510.aplicacion.proveedor.EstadoMock
import fiuba.materia7510.aplicacion.proveedor.Flujo
import fiuba.materia7510.aplicacion.proveedor.FlujoMock
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
	
	/**
	 * EJEMPLO ESTRUCTURA ESTADO REGLAMENTO FULL
	 * 
	 * {:Contadores {:Ticket-Contador [[] true [0]], :Ticket-Contador-Propietario [[(current "propietario")] true {}], :Ticket-Contador-Rojo [[] (starts-with? (current "descripcion") "R") [0]], :Ticket-Contador-Titulo [[current "titulo"] true {}], :email-count [[] true [0]], :spam-count [[] (current "spam") [0]], :spam-important-table [[(current "spam") (current "important")] true {}]}, :ContadorSteps {}, :Sennales {:Ticket-fraction-Rojo [(/ (counter-value "Ticket-Contador-Rojo" []) (counter-value "Ticket-Contador" [])) nil], :spam-fraction [(/ (counter-value "spam-count" []) (counter-value "email-count" [])) true]}, :DatosPasados {}}))
	 * 
	 * */
 	 		
 static def crearReglas(){
	 
	 	
		Factoria.instance.crearRegla(Constantes.REGLAMENTO_FULL, Constantes.REGLA_FULL).save(failOnError:true, flush:true).refresh()
		
		Factoria.instance.crearRegla(Constantes.REGLAMENTO_1,	Constantes.REGLA_PRIMERA).save(failOnError:true, flush:true).refresh()
		
		Factoria.instance.crearRegla(Constantes.REGLAMENTO_2,	Constantes.REGLA_SEGUNDA).save(failOnError:true, flush:true).refresh()
		
	 
	 }
 static def inicializarEstadoMotor(){
	
	/**
		 *  "Nota IMPORTANTE: el retorno del MotorService lo toma como,
		 *  class clojure.lang.PersistentArrayMap, entonces falla."
		 * Por lo tanto, para persistirlo temporalmente lo defino como String
		 *  y no como def. Observar las diferencias."
		 * Pero en la ejecucion por grails console esto no pasa.Misterio. 
		 * Sí se observa una demora importante de varios segundos la primer ejecucion. 
		 * Luego respuesta inmediata."
		 * */
		
		def estad = MotorService.inicializar_procesador(Constantes.REGLAMENTO_FULL)
	estad
}
	
/**
* Veifica existencia de Estadios creados, sino recrear.
* Son instancias unicas. Clave primaria:nombre.
* */		
 static def guardarEstadoMotor(String estado, String nombre){

	def estadio_motor = Estadio.findByNombre(nombre)
		
	if (!estadio_motor){
		
		Factoria.instance.crearEstadio(estado, nombre).save(failOnError:true, flush:true).refresh()	
	
		println "***Estadio guardado ${nombre}. (Estado-STATE- de motor API clojure)***"	
	
	}

}
/*NOTA: Si bien es analogo sendos métodos guardar...; se consideran separados ante el 
 * cambio de diseño tras consulta pendiente al respecto (sobre señales). Lo que implica nada mas,
 * que una nueva entidad para persistir.
 * De este modo queda preparado ante una extensión, sin efectos colaterales(adversos).
 * */
/**
* Verifica existencia de Estadios creados, sino recrear.
* Son instancias unicas. Clave primaria:nombre.
* */		
 static def guardarEstadio(String estadio, String nombre){
	def estadio_encontrado = Estadio.findByNombre(nombre)
		
		if (!estadio_encontrado){
			Factoria.instance.crearEstadio(estadio, nombre).save(failOnError:true, flush:true).refresh()	
			println "***Estadio ${nombre} guardado ***"	
		
		}
 }
 static def cargar_Datos(){
		
		println "***********************"
		println "*                     *"
		println "*       INICIAR       *"
		println "*                     *"
		println "*                     *"
		println "***********************"
			
		println "***********************"
		println "*       REGLAS        *"
		println "*    (Para testeos)   *"
		println "***********************"
		
		/**
		 * Creacion de reglas.
		 * */
		crearReglas()
			
		println "***FIN CREACION REGLAS ***"
		println ""
		
		/**
		 * Fin creacion reglas. 
		 * */
		
		println ""
		println Regla.getAll()
		println ""
		//println Regla.getAll().dump()
		println Regla.dump()
		println ""
		
		println ""	
		println "***********************"
		println "*       ESTADO        *"
		println "*                     *"
		println "*       MOTOR         *"
		println "*                     *"
		println "*    INICIALIZANDO    *"
		println "*                     *"
		println "***********************"
		println ""
		
		def estad = inicializarEstadoMotor()
		String estatus = estad
		
		println ""
		println "***"
		println "Estado inicializado:... ${estad}"
		println ""
		println estad.getClass()
		println ""
		println "***"
		println ""
		println "ESTADO inicial:(definido como string)::${estatus}"
		println ""
		println estatus.class
		println "***"
		println ""

		println "***********************"
		println "*      ESTADIO        *"
		println "*  Estado Inicial     *"
		println "*     	 Motor         *"
		println "*    API CLOJURE      *"
		println "*                     *"
		println "*     Guardando       *"
		println "*                     *"
		println "***********************"
		
		/**
		 * Persisto estado inicial de motor-API CLOJURE- configurado según reglas.
		 * Cuidado con el parametro que es un string para la clase Estadio.
		 * */
		
		guardarEstadoMotor(estatus, Constantes.NOMBRE_ESTADIO_MOTOR_DESARROLLO)
		
		guardarEstadoMotor(estatus, Constantes.NOMBRE_ESTADIO_MOTOR_PRODUCCION)
				
		
		println ""
		//println Estadio.getAll()
		println ""
		println Estadio.dump()
		//println "_______________________"
		//println Estadio.list().each { println  it.dump()}
		println ""	
		
		println ""
		println "***********************"
		println "*     Definiendo      *"
		println "*     estructura      *"
		println "*     Json basica     *"
		println "*     extensible      *"
		println "*     (Plantilla)     *"
		println "***********************"
		
		/**
		 * El ticket no se almacena pero se envia,al consumidor final, a posteriori sin necesidad de persistencia(opcional).
		 * */
	
		println ""
		println Constantes.ESTRUCTURA_JSON_BASICA_EXTENSIBLE
		println ""
		println "***********************"
		println "*     Verificando      *"
		println "*     Estadios         *"
		println "*     iniciales.       *"
		println "*     Sino existen,    *"
		println "*     crear.           *"
		println "***********************"
		
		/**
		 * Verificando Estadios creados, sino recrear.
		 * */
		 
		guardarEstadio(Constantes.ESTRUCTURA_JSON_BASICA_EXTENSIBLE, Constantes.NOMBRE_ESTADIO_PLANTILLA_JSON)
		
		guardarEstadio(Constantes.ESTRUCTURA_JSON_BASICA_EXTENSIBLE, Constantes.NOMBRE_ESTADIO_PLANTILLA_JSON_DESARROLLO)
		 
		guardarEstadio(Constantes.ESTRUCTURA_JSON_BASICA_EXTENSIBLE, Constantes.NOMBRE_ESTADIO_PLANTILLA_JSON_PRODUCCION)
		
		println Estadio.getAll()
		
		println ""
		println "***Verificación terminada***"	
		println ""
		
		println ""	
		println "***********************"
		println "*                     *"
		println "*       Creando       *"
		println "*     datos para      *"
		println "*       testeo        *"
		println "*    y/o ejemplos     *"
		println "*      (tickets)      *"
		println "***********************"
		println ""
		
		String tit1	= 	'email-count'
		String tit2	=	'spam-count'
		String color="Soy un color."
		String alimento ="Soy un alimento."
		def f =null
		def e1 = Factoria.instance.crearEstadoMock (Colores.VERDE.getColor(),color).save(failOnError:true, flush:true)
		def e2 = Factoria.instance.crearEstadoMock (Colores.AMARILLO.getColor(),color).save(failOnError:true, flush:true)
		def e3 = Factoria.instance.crearEstadoMock (Colores.ROJO.getColor(),color).save(failOnError:true, flush:true)
		def e4 = Factoria.instance.crearEstadoMock (Alimentos.PASTAS.getAlimento(),alimento).save(failOnError:true, flush:true)
		def e5 = Factoria.instance.crearEstadoMock (Alimentos.CARNES.getAlimento(),alimento).save(failOnError:true, flush:true)
		def e6 = Factoria.instance.crearEstadoMock (Alimentos.VEGETALES.getAlimento(),alimento).save(failOnError:true, flush:true)
		
		f =	Factoria.instance.crearFlujoMock("Flujo-Desarrollo-A","-Prueba-A-COLORES")
		
		f?.addToEstados (e1)
		
		f?.addToEstados (e2)
		
		f?.addToEstados (e3)
		
		f?.save(failOnError:true, flush:true)
		
		Factoria.instance.crearTicketMock(e1, f, 1, tit1,"MockA", "AA").save(failOnError:true, flush:true)
		
		
		Factoria.instance.crearTicketMock(e2, f,2, tit1, "MockB", "BB").save(failOnError:true, flush:true)
		
		 
		Factoria.instance.crearTicketMock(e3, f, 3, tit1, "MockC", "CC").save(failOnError:true, flush:true)
		
		f =	Factoria.instance.crearFlujoMock("Flujo-Desarrollo-B","-Prueba-B-ALIMENTOS")
		
		f?.addToEstados (e4)
		
		f?.addToEstados (e5)
		
		f?.addToEstados (e6)
		
		f?.save(failOnError:true, flush:true)
			
			
		Factoria.instance.crearTicketMock( e4, f, 4, tit2, "MockD", "DD").save(failOnError:true, flush:true)
			
			 
		Factoria.instance.crearTicketMock( e5, f, 5, tit2, "MockE", "EE").save(failOnError:true, flush:true)
			
			 
		Factoria.instance.crearTicketMock( e6, f, 6, tit2, "MockF", "FF").save(failOnError:true, flush:true)
			
		/*******************************************/
	   
		
		Factoria.instance.crearTicketMock( e3, f, 12345, "AUGUSTO", "EMISOR: AUGUSTO RECEPTOR: Arevalo", "Propiedad de Arevalo").save(failOnError:true, flush:true)
		 
		Factoria.instance.crearTicketMock( e1, f, 54321, "AUGUSTO", "RETADOR: AUGUSTO SUSODICHO: Arevalo", "Propiedad de Arevalo").save(failOnError:true, flush:true)
		
		Factoria.instance.crearTicketMock( e1, f, 111, "Euriterco", "RETADOR: AUGUSTO SUSODICHO: Arevalo", "Propiedad de Arevalo").save(failOnError:true, flush:true)
		
		Factoria.instance.crearTicketMock( e1, f, 222, "Rascondio", "RIVAL: Rascondio", "Rascondio").save(failOnError:true, flush:true)
		
		Factoria.instance.crearTicketMock( e2, f, 1111, "Rasputin", "Retorno a produccion", "Rosalinda").save(failOnError:true, flush:true)
		
		/****************************************************************************************************/
		
		println "***********************"
		println "*                     *"
		println "*      Fin Inicio.    *"
		println "*                     *"
		println "***********************"
		println ""
		
}//fin metodo cargar_datos
 static  def probar_TicketsMock_con_Motor(){
		
		println "________Procesando con motor clojure__________"
		
		def estadoActualizandose = Estadio.findByNombre(Constantes.NOMBRE_ESTADIO_MOTOR_DESARROLLO)
		println "___________________________"
		println "Estado inicial: "
		
		println estadoActualizandose.toString() 
		println "[JSON intancia Estadio]"
		def tmp = estadoActualizandose as JSON
		
		/**El formato resultante de tmp.toString es el aceptado por cheshire-plugin-clojure*/
		println tmp.toString()
		println "___________________________"
		println "[Propiedad de instancia JSON:(el estado-state- de api clojure)]"
		println estadoActualizandose.estadio
		println "___________________________"
		def contador = 0
		/*Porque son pocos se puede iterar de esta manera. Sino es malo cargar en memoria.*/
		TicketMock.list().each { elemento ->
			contador ++
			println "[*********ITERACION NUMERO ${contador}***************]"
			def ele_JSON = elemento as JSON
			println "_______________________"
			println "_"
			println "[ITERANDO]Información de tickets Mock: "	
			println ele_JSON.toString()
			println "_"
			println ele_JSON.getClass()
			println "Convirtiendo a String:"
			
			String ele_JSON_casteado_str = ele_JSON.toString()
			
			//println ele_JSON_casteado_str
			println "[EDITANDO]"
			String dato_editado = "\'" + ele_JSON_casteado_str + "\'"
			String dato_reemplazado_caracteres = ele_JSON_casteado_str.replace('"','\\"')
			String dato_editado_reemplazado_caracteres = "\"" + dato_reemplazado_caracteres + "\""
			println "_"
			//println dato_editado 
			println "_"
			//println dato_reemplazado_caracteres
			//println "_"
			//println dato_editado_reemplazado_caracteres
			//println dato_editado_reemplazado_caracteres.getClass()
			def dato_redefinido = dato_editado_reemplazado_caracteres
			println "_"
			println dato_redefinido
			println dato_redefinido.getClass()
			println "_______________________"
			
			/**Formato de clave true retorna el mapa con claves "Keyword en Clojure"**/		
		
			println "_"
			
			def ele_formato_clojure = MotorService.decodificar_JsonFormato_a_MapFormatoClojure (dato_redefinido, "true")	
			
			println "_______________________"
			println "TicketMock formato JSON convertido a formato map Clojure:"
			println ele_formato_clojure
			println "[EDITANDO] a String:"
			String dato_map_clojure = ele_formato_clojure
			println dato_map_clojure
			println "_______________________"
			
			def st1 		= MotorService.process_data_dropping_signals( estadoActualizandose.estadio, dato_map_clojure)
			def sennales 	= MotorService.procesar_datos_recuperar_sennales( estadoActualizandose.estadio, dato_map_clojure)
			
			println "Estado nuevo: "
			println st1
			println "Señales: "
			println sennales
			
			estadoActualizandose.estadio = st1
			
			println "Estado actualizado: "
			
			println estadoActualizandose.estadio
			
			println "[*******FINAL ITERACION NUMERO ${contador}***********]"
		
		}//fin clausura
		
		println "*********ITERACION FINALIZADA *********"
		println "___________________Fin procesando datos de prueba con motor clojure_________________"
	
}	
 static def preparar_Estructura_para_graficar(){
		
		println "********Preparando para graficar*******"
		
		println ""
		
		def estadoActualizandose = Estadio.findByNombre("Primero")
		
		def mapaGraficable = MotorService.mapear_aplanar_resultados_del_Estado (estadoActualizandose.estadio, ':Contadores')
		
		println "Estructura útil para gráficos:(con def)"
		
		String mapaGraficable_str = mapaGraficable
		
		println "Estructura útil para gráficos:(casteada a String) "
		
		println mapaGraficable_str
		
		println "Estructura útil para gráficos :(formato Json)"
		
		def mapaGraficable_Json = MotorService.codificar_MapFormatoClojure_a_JsonFormato (mapaGraficable)
		
		println "Retorno mapa Formato Clojure a Json:"
		
		println  mapaGraficable_Json
		
		println "Retorno de señales aplanadas sin guardar por ahora(test): "
		
		println MotorService.mapear_aplanar_resultados_del_Estado (estadoActualizandose.estadio,'Sennales')
		
		
	}
 static def borrar_instancia_Estadio(String nombre){	
	/*Borrar Estadio*/
		Estadio.findByNombre(nombre)?.delete(flush:true)		
	}
 static def borrar_instancia_Regla(String nombre){
	Regla.findByNombre(nombre)?.delete(flush:true)
	}	
 static def destruir_Datos(){
		/*NOTA:
		 * 
		 * DomainClass.findAll().each { it.delete(flush:true, failOnError:true) }
		 * 
		 * GORM does not provide a deleteAll method as deleting data must be done with caution.
		 * To delete data you can use executeUpdate. 
		 * 
		 * DomainClass.executeUpdate('delete from DomainClass')
		 * */
		
		println "***********************"
		println "*                     *"
		println "* Borrando datos mock *"
		println "*                     *"
		println "***********************"
		println ""
		println "Antes:"
		println TicketMock.count()
		
		/*Borrar tickets_Mock*/
		TicketMock.executeUpdate('delete from TicketMock')
		
		println "Despues: "
		println TicketMock.count()
		
		println "***********************"
		println "*                     *"
		println "* Borrando ReglasMock *"
		println "*                     *"
		println "***********************"
		
		/*Borrar Reglas*/
		borrar_instancia_Regla(Constantes.REGLA_FULL)
		borrar_instancia_Regla(Constantes.REGLA_PRIMERA)
		borrar_instancia_Regla(Constantes.REGLA_SEGUNDA)
		
		println "***********************"
		println "*                     *"
		println "*      Borrando       *"
		println "*     Estado Motor    *"
		println "***********************"
		
		println "Antes:"
		println Estadio.count()
		
		/*Borrar Estadio*/
		borrar_instancia_Estadio(Constantes.NOMBRE_ESTADIO_MOTOR_DESARROLLO)
		
		
		println "Despues:"
		println Estadio.count()
		println "Verificando Existe Estado Motor:"
		def estadio_motor = Estadio.findByNombre(Constantes.NOMBRE_ESTADIO_MOTOR_DESARROLLO)
		println estadio_motor
		
		println "***********************"
		println "*                     *"
		println "*      Borrando       *"
		println "*     Estadio_Mock    *"
		println "*   Plantilla para    *"
		println "*     Graficacion     *"
		println "*      (Testeo)       *"
		println "*                     *"
		println "***********************"
		
		println "Antes:"
		println Estadio.count()
		
		/*Borrar Estadio*/
		borrar_instancia_Estadio(Constantes.NOMBRE_ESTADIO_PLANTILLA_JSON_DESARROLLO)
		
		println "Despues:"
		println Estadio.count()
		println "Verificando Existe Estado Motor:"
		def estadio_mock_graficable = Estadio.findByNombre(Constantes.NOMBRE_ESTADIO_PLANTILLA_JSON_DESARROLLO)
		println estadio_mock_graficable
		println ""
		println "***********************"
		println "* 	    Borrando       *"
		println "*       FLUJOS        *"
		println "***********************"
		println ""
		
		println "Antes-cantidad de instancias Flujo:"
		println FlujoMock.count()
		/*Borrar Flujos establecido borrado en cascada*/
		/*Entonces se borran los Estados (hijos)*/
		FlujoMock.executeUpdate('delete from FlujoMock')
		println "Despues-cantidad de instancias Flujo:"
		println FlujoMock.count()
		
		println ""
		println "***********************"
		println "* 	    Borrando       *"
		println "*     EstadosMock     *"
		println "***********************"
		println ""
		println "Estados Mock existentes"
		println()
		println Estado.list()*.estado
		println()
		println "Antes-cantidad de instancias Estado-:"
		println EstadoMock.count()
		
		/*Borrar Estado*/
		//Borrar esto sin haber borrado flujo, da violacion de integridad referencial.
		EstadoMock.executeUpdate('delete from EstadoMock')
		println "Despues:"
		println EstadoMock.count()
		
	}
}//fin clase
