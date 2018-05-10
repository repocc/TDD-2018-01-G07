package fiuba.materia7510.aplicacion.proveedor

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class FlujoController {

    FlujoService flujoService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond flujoService.list(params), model:[flujoCount: flujoService.count()]
    }

    def show(Long id) {
        respond flujoService.get(id)
    }

    def create() {
        respond new Flujo(params)
    }

    def save(Flujo flujo) {
        if (flujo == null) {
            notFound()
            return
        }

        try {
            flujoService.save(flujo)
        } catch (ValidationException e) {
            respond flujo.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'flujo.label', default: 'Flujo'), flujo.id])
                redirect flujo
            }
            '*' { respond flujo, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond flujoService.get(id)
    }

    def update(Flujo flujo) {
        if (flujo == null) {
            notFound()
            return
        }

        try {
            flujoService.save(flujo)
        } catch (ValidationException e) {
            respond flujo.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'flujo.label', default: 'Flujo'), flujo.id])
                redirect flujo
            }
            '*'{ respond flujo, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        flujoService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'flujo.label', default: 'Flujo'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'flujo.label', default: 'Flujo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
