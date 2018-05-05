package dashboard

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class InstrumentoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Instrumento.list(params), model:[instrumentoCount: Instrumento.count()]
    }

    def show(Instrumento instrumento) {
        respond instrumento
    }

    def create() {
        respond new Instrumento()
    }

    @Transactional
    def save(Instrumento instrumento) {
        if (instrumento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (instrumento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond instrumento.errors, view:'create'
            return
        }

        instrumento.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'instrumento.label', default: 'Instrumento'), instrumento.id])
                redirect instrumento
            }
            '*' { respond instrumento, [status: CREATED] }
        }
    }

    def edit(Instrumento instrumento) {
        respond instrumento
    }

    @Transactional
    def update(Instrumento instrumento) {
        if (instrumento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (instrumento.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond instrumento.errors, view:'edit'
            return
        }

        instrumento.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'instrumento.label', default: 'Instrumento'), instrumento.id])
                redirect instrumento
            }
            '*'{ respond instrumento, [status: OK] }
        }
    }

    @Transactional
    def delete(Instrumento instrumento) {

        if (instrumento == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        instrumento.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'instrumento.label', default: 'Instrumento'), instrumento.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'instrumento.label', default: 'Instrumento'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
