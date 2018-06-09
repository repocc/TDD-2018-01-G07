package fiuba.materia7510.aplicacion.proveedor

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class TicketController {

    TicketService ticketService

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
            ticketService.save(ticket)
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
}
