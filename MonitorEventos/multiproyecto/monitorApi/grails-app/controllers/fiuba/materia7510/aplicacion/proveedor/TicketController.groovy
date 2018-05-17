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
		
		
		println "Procesar invocado..."
		if ((ticket_mock == null) && TicketMock.count())
		{ 
			
			ticket_mock = TicketMock.first()
			 
		}
		
		if ((Regla.reglamento != null) && (ticket_mock != null)){
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
				
				ticket_mock?.delete(flush:true)
				
				Estadio.estatus = st1
				def cuenta = 1
				
					println "Enviando ...: ${contadorTotal}"				
					render(view: "graficos", model:	[value: contadorTotal])
					println "durmiento........."
					sleep (1000)
				}
			}
			else{
				println "No hay TICKET. "
			}
	}
	def dibujar (){
		
		render view: "graficos"
		}
		
		
	def enviar(){
			println "solicitado dato Action enviar..."								
			//render ([value: 10] as JSON)
			respond ([value: 10], status:200)
			println "Enviado dato Action enviar."
			sleep (1000)		
	}
		
}








			/*
			render(view: "graficos", 	model: 	[consulta:	[contadorTotal: ct, contador1: cr],
										dato: 	[ticket:	ticket_mock]])
			
			*/
										
										
			/*respond(	[consulta:	[contadorTotal: ct, contador1: cr],
							dato: 	[ticket:ticket_mock]] , status: 200) 
			*/
			
