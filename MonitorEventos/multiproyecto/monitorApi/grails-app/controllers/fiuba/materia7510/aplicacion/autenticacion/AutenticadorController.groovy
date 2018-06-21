package fiuba.materia7510.aplicacion.autenticacion

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class AutenticadorController {

    AutenticadorService autenticadorService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond autenticadorService.list(params), model:[autenticadorCount: autenticadorService.count()]
    }

    def show(Long id) {
        respond autenticadorService.get(id)
    }

    def create() {
        respond new Autenticador(params)
    }

    def save(Autenticador autenticador) {
        if (autenticador == null) {
            notFound()
            return
        }

        try {
            autenticadorService.save(autenticador)
        } catch (ValidationException e) {
            respond autenticador.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'autenticador.label', default: 'Autenticador'), autenticador.id])
                redirect autenticador
            }
            '*' { respond autenticador, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond autenticadorService.get(id)
    }

    def update(Autenticador autenticador) {
        if (autenticador == null) {
            notFound()
            return
        }

        try {
            autenticadorService.save(autenticador)
        } catch (ValidationException e) {
            respond autenticador.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'autenticador.label', default: 'Autenticador'), autenticador.id])
                redirect autenticador
            }
            '*'{ respond autenticador, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        autenticadorService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'autenticador.label', default: 'Autenticador'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'autenticador.label', default: 'Autenticador'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
