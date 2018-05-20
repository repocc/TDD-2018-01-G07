package fiuba.materia7510.aplicacion.proveedor

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

import grails.converters.JSON

import fiuba.materia7510.aplicacion.generador.Estadio
import fiuba.materia7510.aplicacion.generador.Regla

import fiuba.materia7510.aplicacion.MotorService


class TicketController {

    TicketService ticketService
	
	TicketMock ticket_mock = null
    
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
			
			Ticket tic = ticketService.save(ticket)
            
            
            //chain(action: "procesar", model: [ticket: tic])
            
            //forward para ir a otro lugar sin un request Http.
            //forward controller: "motor", action: "procesar", params: ticket.properties
         
            //redirect(controller:"motor", action: "sampleAction")
            
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
	 def procesar(){
		
		//ticket_mock = chainModel.ticket
		
		println "Procesar invocado..."
		
		boolean procesado = false
		
		ticket_mock = TicketMock.first()
		
		if (ticket_mock == null) 
		{ 
			/**Se envía mensaje pero sino tomar de la base de datos conviene, e ir borrando*/
			flash.message = "EL objeto ticket mock es NULL. Se cancela petición. ERROR 204:"
			render(status: 204, text: 'No hay tickets.')
			
			/**
			204 No Content:La petición se ha completado con éxito,
			pero su respuesta no tiene ningún contenido, 
			aunque los encabezados pueden ser útiles. 
			El agente de usuario puede actualizar sus encabezados en caché, 
			para este recurso con los nuevos valores.

			*/
			 
		}
		else (Regla.reglamento != null){
			/**************PROCESAMIENTO**************************/
			def contadorTotal = 0
			def ct= 0
			def cr= 0
			def reglas           = Regla.reglamento
			def nombre_contadorTotal 	= 'email-count'
			def nombre_contador1		= 'spam-count'
			def dato 			= ticket_mock.titulo		
			def arg		= '[]'
			
			def st0 = Estadio.estatus
			
			def retorno=	MotorService.inicializar_procesador (Regla.reglamento)?:null
			
			def st1 = MotorService.process_data_dropping_signals( st0 ,dato)?:null
        
			 contadorTotal = MotorService.consultar_contador( st1, nombre_contadorTotal,arg)?:null
			
			//def contador1 	  =	MotorService.consultar_contador( st2, nombre_contador1,arg)?:null
			
			 println "Contador Total: ${contadorTotal}"
			 
			
			if ((st1 != null) && (contadorTotal != null))	{
				
				println "Enviando ...: ${ticket_mock}"
				
				
				
				Estadio.estatus = st1
				
				
				println "Enviando ...: ${contadorTotal}"				
				
				render( model:	[valor: contadorTotal, etiqueta: nombre_contadorTotal ])
				
				
				println "durmiento........."
				
				sleep (1000)
				
				procesado = true
				}
			/**********************FIN PROCESAMIENTO*******************/	
			}
			println("Eliminando ticket de la base de datos")
			
			procesado?ticket_mock?.delete(flush:true):false
}
	
	def renderizar_Tickets(){
		
		render view: "graficos"
		}
		
		
	def enviar(){
			println "solicitado dato Action enviar..."
											
			render ([valor: 10, etiqueta:"contadorTotal" ], status: 200)
			//render ([value: 10] as JSON)
			//respond ([value: 10], status:200)
			sleep (2000)		
	}
		
}








			/*
			render(view: "graficos", 	model: 	[consulta:	[contadorTotal: ct, contador1: cr],
										dato: 	[ticket:	ticket_mock]])
			
			*/
										
										
			/*respond(	[consulta:	[contadorTotal: ct, contador1: cr],
							dato: 	[ticket:ticket_mock]] , status: 200) 
			*/
			
