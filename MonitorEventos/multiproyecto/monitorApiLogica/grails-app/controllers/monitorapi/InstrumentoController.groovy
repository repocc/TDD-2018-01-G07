package monitorapi

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class InstrumentoController {

    InstrumentoService instrumentoService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond instrumentoService.list(params), model:[instrumentoCount: instrumentoService.count()]
    }

    def show(Long id) {
        respond instrumentoService.get(id)
    }

    def create() {
        respond new Instrumento(params)
    }

    def save(Instrumento instrumento) {
        if (instrumento == null) {
            notFound()
            return
        }

        try {
            instrumentoService.save(instrumento)
        } catch (ValidationException e) {
            respond instrumento.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'instrumento.label', default: 'Instrumento'), instrumento.id])
                redirect instrumento
            }
            '*' { respond instrumento, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond instrumentoService.get(id)
    }

    def update(Instrumento instrumento) {
        if (instrumento == null) {
            notFound()
            return
        }

        try {
            instrumentoService.save(instrumento)
        } catch (ValidationException e) {
            respond instrumento.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'instrumento.label', default: 'Instrumento'), instrumento.id])
                redirect instrumento
            }
            '*'{ respond instrumento, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        instrumentoService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'instrumento.label', default: 'Instrumento'), id])
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
