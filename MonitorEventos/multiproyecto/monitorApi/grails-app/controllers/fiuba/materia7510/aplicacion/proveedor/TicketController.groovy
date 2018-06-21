package fiuba.materia7510.aplicacion.proveedor

//import grails.rest.RestfulController
import grails.gorm.transactions.*
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

import grails.converters.JSON

import fiuba.materia7510.aplicacion.generador.Estadio
import fiuba.materia7510.aplicacion.generador.Regla
import fiuba.materia7510.aplicacion.utilidad.TesterBootstrap
import fiuba.materia7510.aplicacion.utilidad.Constantes
import fiuba.materia7510.aplicacion.utilidad.Impresor
import fiuba.materia7510.aplicacion.MotorService
import fiuba.materia7510.aplicacion.proveedor.ProcesadorTicketService
import fiuba.materia7510.aplicacion.proveedor.Dato
import fiuba.materia7510.aplicacion.proveedor.TicketMock

//Static scaffolding

class TicketController {
 
    TicketService ticketService
	
	ProcesadorTicketService procesadorTicketService   
    
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ticketService.list(params), model:[ticketCount: ticketService.count()]
    }

    def show(Long id) {
        respond ticketService.get(id)
    }

    def create() {
				respond new Ticket(params)   
    }
		
    def save(Ticket ticket) {
        if (ticket == null) {
            notFound()
            return
        }

        try {
						def validado = ticket.validarEstadoPerteneceFlujo()
						
						if( validado == null){
					
								flash.message = "El Estado [${validado}] elegido, no pertenece al flujo definido. Verifique."
					
								render view:"create"
								
								throw new ValidationException("Estado no pertenece a flujo", ticket.errors)
								
								return
						}
			      
			      Ticket tic = ticketService.save(ticket)
                       
        } catch (ValidationException e) {
            respond ticket.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'ticket.label', default: 'Ticket'), ticket.id])
                redirect ticket
            }
            '*' { respond ticket, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond ticketService.get(id)
    }
		
		
    def update(Ticket ticket) {
        if (ticket == null) {
            notFound()
            return
        }

        try {
						def validado = ticket.validarEstadoPerteneceFlujo()
						
						if( validado == null){
							
							flash.message = "El Estado [${validado}] elegido, no pertenece al flujo definido. Verifique."
							respond ticketService.get(ticket.id)
							return
						}
            ticketService.save(ticket)
        } catch (ValidationException e) {
            respond ticket.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ticket.label', default: 'Ticket'), ticket.id])
                redirect ticket
            }
            '*'{ respond ticket, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        ticketService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ticket.label', default: 'Ticket'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'ticket.label', default: 'Ticket'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

	/**NOTA IMPORTANTE 
	 * 
	 * Due to the way Groovy works, 
	 * while responding with a Map and arguments 
	 * it is important to respond in this manner 
	 * respond([:], status: 200) instead of this manner 
	 * respond([:], [status: 200]). 
	 * Failure to do so will result in the arguments being reversed.
	 * 
	 * */
	
	/**
	 * Action enviarTicketsMockProcesados: Funcionalidad completa
	 * de la aplicación con datos y reglas de prueba.
	 * 
	 * */	
	def enviarTicketsMockProcesados(){
		/**
		 * Recuperando datos-tickets-mock
		 * */
		
		def sql 		= "SELECT count(*) FROM TicketMock"
		//"SELECT count(*) FROM TicketMock as T WHERE T.class = fiuba.materia7510.aplicacion.proveedor.TicketMock"
		def cantidad 	= TicketMock.executeQuery(sql) //es un array la respuesta a la consulta.
		
		//Esto no funciona a causa de Table-per-Class
		//int cantidad_Tickets 	= TicketMock.count()
		
		/*Si no hay ticketsMocks, se regeneran. */
		
		Impresor.instance.consola("Datos Mock existentes: ${cantidad} .",true)
		
		if (cantidad[0] == 0){ 
			
			TesterBootstrap.destruir_Datos()
			TesterBootstrap.cargar_Datos()
		}
		
		TicketMock ticketMock 	= TicketMock.first()
		
		/**
		 * SERVICIO ProcesadorTicketService: Procesa dato-ticket.
		 * */
		/**
		 * Procesamiento implica:
		 * - Aplicando Motor API Clojure.
		 * - Reestructurando la "estructura provista por Motor", 
		 * de modo de ser apta, para la mayoría de herramientas para graficar.
		 * (Para ello, la funcionalidad principal está implementada en Clojure).
		 * - Extrayendo datos útiles para graficar (Timestamp). Parsing.
		 * - Calculando tiempo de procesamiento (promedio y tiempo unitario expresado en milisegundos).
		 * - Agregando el dato a la estructura para enviarlo al consumidor.
		 * - Retorna estructura en formato Json.
		 * 
		 * */
		//el retorno es un JsonString.
		def dato_procesado_JsonString = ticketMock?procesadorTicketService.procesar(ticketMock,Constantes.NOMBRE_ESTADIO_PLANTILLA_JSON_DESARROLLO,Constantes.NOMBRE_ESTADIO_MOTOR_DESARROLLO):null
		
		/**
		 * Fin procesamiento dato-ticket
		 * */
		//borrando dato ticket procesado.
		ticketMock?.delete(flush:true)
		String msg_dato_solicitado ="Datos solicitados: enviando...${dato_procesado_JsonString}"
		Impresor.instance.consola(msg_dato_solicitado,true)
		
		//enviando dato solicitado, formato Json.
		render dato_procesado_JsonString
				
	}//fin enviarTickets
				
	/**
	 * Action enviar:Basico para rapido testeo HTTPrequest
	 * */			
	def enviarDatoJsonPrueba(){
			Impresor.instance.consola( "Solicitado dato; enviando...",false)
				
				sleep (1000)
				render ([valor: 10, etiqueta:"contadorTotal" ] as JSON)
									
	}//enviar
	
	/**
	 * Action solicitarDatos: 
	 * Retorna los datos-tickets, solicitados por el consumidor final,
	 * provistos por el distribuidor de tickets.
	 * Un servicio es utilizado para el procesamiento.
	 * */	
	def solicitarDato(){
		
		def sql 		= "SELECT count(*) FROM Dato as T WHERE T.class = fiuba.materia7510.aplicacion.proveedor.Ticket"
		def cantidad 	= Ticket.executeQuery(sql)
		
		//**Nota personal: En la consulta -def cantidad- retorna class java.util.ArrayList **

		/**
		 * La organización de las tablas por defecto en Grails es TablePerClass- Pero configurable a TablePerHierarchy -.
		 * El ejemplo siguiente: Supervisor hereda de Empleado
		 * SELECT ID, FIRST_NAME, LAST_NAME FROM   EMPLOYEE WHERE  CLASS = 'emmanuel.rosa.grailsinheritanceexample.Supervisor'
		 * */
		Impresor.instance.consola("Cantidad de DATOS-TICKETS existentes: ${cantidad}.",true)
		
		if (cantidad[0] == 0){ 
			
			//respond([:] as JSON, status: 204)
			render(status: 204, text: 'Lo lamento, se acabaron los Datos/Tickets. Consulte en otro momento si el Distribuidor se puso las pilas. Muchas gracias y disculpe la molestia.')
			
			return
		}
		
		Ticket ticket 	= Ticket.first()
		
		/*Procesando tickets*/
		/**
		 * Aplicando Motor API Clojure
		 * Reestructurando dato para graficar.
		 * Extrayendo datos utiles para graficar (Timestamp). Parsing.
		 * Calculando tiempo de procesamiento (promedio y tiempo unitario expresado en milisegundos)
		 * Agregando el dato a la estructura para enviarlo al consumidor.
		 * 
		 * */
		//el retorno es un JsonString
		def dato_procesado_JsonString = ticket?procesadorTicketService.procesar(ticket,Constantes.NOMBRE_ESTADIO_PLANTILLA_JSON_PRODUCCION,Constantes.NOMBRE_ESTADIO_MOTOR_PRODUCCION):null
		
		
		//borrando dato ticket procesado
		ticket?.delete(flush:true)
		
		Impresor.instance.consola("Datos solicitados: enviando...${dato_procesado_JsonString}")
		
		render dato_procesado_JsonString
	}
		
}

