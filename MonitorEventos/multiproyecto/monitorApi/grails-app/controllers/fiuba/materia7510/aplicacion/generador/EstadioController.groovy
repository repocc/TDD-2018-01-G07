package fiuba.materia7510.aplicacion.generador

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class EstadioController {

    EstadioService estadioService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond estadioService.list(params), model:[estadioCount: estadioService.count()]
    }

    def show(Long id) {
        respond estadioService.get(id)
    }

    def create() {
        respond new Estadio(params)
    }

    def save(Estadio estadio) {
        if (estadio == null) {
            notFound()
            return
        }

        try {
            estadioService.save(estadio)
        } catch (ValidationException e) {
            respond estadio.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'estadio.label', default: 'Estadio'), estadio.id])
                redirect estadio
            }
            '*' { respond estadio, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond estadioService.get(id)
    }

    def update(Estadio estadio) {
        if (estadio == null) {
            notFound()
            return
        }

        try {
            estadioService.save(estadio)
        } catch (ValidationException e) {
            respond estadio.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'estadio.label', default: 'Estadio'), estadio.id])
                redirect estadio
            }
            '*'{ respond estadio, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        estadioService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'estadio.label', default: 'Estadio'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'estadio.label', default: 'Estadio'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
