package tickets

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class EstadoTicketController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond EstadoTicket.list(params), model:[estadoTicketCount: EstadoTicket.count()]
    }

    def show(EstadoTicket estadoTicket) {
        respond estadoTicket
    }

    def create() {
        respond new EstadoTicket(params)
    }

    @Transactional
    def save(EstadoTicket estadoTicket) {
        if (estadoTicket == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (estadoTicket.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond estadoTicket.errors, view:'create'
            return
        }

        estadoTicket.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'estadoTicket.label', default: 'EstadoTicket'), estadoTicket.id])
                redirect estadoTicket
            }
            '*' { respond estadoTicket, [status: CREATED] }
        }
    }

    def edit(EstadoTicket estadoTicket) {
        respond estadoTicket
    }

    @Transactional
    def update(EstadoTicket estadoTicket) {
        if (estadoTicket == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (estadoTicket.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond estadoTicket.errors, view:'edit'
            return
        }

        estadoTicket.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'estadoTicket.label', default: 'EstadoTicket'), estadoTicket.id])
                redirect estadoTicket
            }
            '*'{ respond estadoTicket, [status: OK] }
        }
    }

    @Transactional
    def delete(EstadoTicket estadoTicket) {

        if (estadoTicket == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        estadoTicket.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'estadoTicket.label', default: 'EstadoTicket'), estadoTicket.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'estadoTicket.label', default: 'EstadoTicket'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
