package tickets

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TicketController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Ticket.list(params), model:[ticketCount: Ticket.count()]
    }

    def show(Ticket ticket) {
        respond ticket
    }

    def create() {
        respond new Ticket(params)
    }

    @Transactional
    def save(Ticket ticket) {
        if (ticket == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (ticket.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ticket.errors, view:'create'
            return
        }

        ticket.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'ticket.label', default: 'Ticket'), ticket.id])
                redirect ticket
            }
            '*' { respond ticket, [status: CREATED] }
        }
    }

    def edit(Ticket ticket) {
        respond ticket
    }

    @Transactional
    def cambiarEstado(Ticket ticket) {
        if (ticket == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (ticket.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ticket.errors, view:'edit'
            return
        }

        switch(params.NuevoEstado) {
             case   'Primer camino': ticket.estadoCompleto = ticket.estadoCompleto.siguientes.first(); break;
             case  'Segundo camino': ticket.estadoCompleto = ticket.estadoCompleto.siguientes.last() ; break;
             default:
               tablero.errors.add "Tipo no valido"
               respond tablero.errors, view:'edit'
               return
        }
        ticket.estado = ticket.estadoCompleto.nombre


        ticket.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ticket.label', default: 'Ticket'), ticket.id])
                redirect ticket
            }
            '*'{ respond ticket, [status: OK] }
        }
    }

    @Transactional
    def update(Ticket ticket) {
        if (ticket == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (ticket.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ticket.errors, view:'edit'
            return
        }

        ticket.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'ticket.label', default: 'Ticket'), ticket.id])
                redirect ticket
            }
            '*'{ respond ticket, [status: OK] }
        }
    }



    @Transactional
    def delete(Ticket ticket) {

        if (ticket == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        ticket.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'ticket.label', default: 'Ticket'), ticket.id])
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
