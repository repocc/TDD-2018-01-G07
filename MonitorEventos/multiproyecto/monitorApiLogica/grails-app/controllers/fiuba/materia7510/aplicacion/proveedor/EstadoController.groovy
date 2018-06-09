package fiuba.materia7510.aplicacion.proveedor

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class EstadoController {

    EstadoService estadoService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond estadoService.list(params), model:[estadoCount: estadoService.count()]
    }

    def show(Long id) {
        respond estadoService.get(id)
    }

    def create() {
        respond new Estado(params)
    }

    def save(Estado estado) {
        if (estado == null) {
            notFound()
            return
        }

        try {
            estadoService.save(estado)
        } catch (ValidationException e) {
            respond estado.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'estado.label', default: 'Estado'), estado.id])
                redirect estado
            }
            '*' { respond estado, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond estadoService.get(id)
    }

    def update(Estado estado) {
        if (estado == null) {
            notFound()
            return
        }

        try {
            estadoService.save(estado)
        } catch (ValidationException e) {
            respond estado.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'estado.label', default: 'Estado'), estado.id])
                redirect estado
            }
            '*'{ respond estado, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        estadoService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'estado.label', default: 'Estado'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'estado.label', default: 'Estado'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
