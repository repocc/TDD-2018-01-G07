package monitorapi

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class MotorDeDatosController {

    MotorDeDatosService motorDeDatosService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond motorDeDatosService.list(params), model:[motorDeDatosCount: motorDeDatosService.count()]
    }

    def show(Long id) {
        respond motorDeDatosService.get(id)
    }

    def create() {
        respond new MotorDeDatos(params)
    }

    def save(MotorDeDatos motorDeDatos) {
        if (motorDeDatos == null) {
            notFound()
            return
        }

        try {
            motorDeDatosService.save(motorDeDatos)
        } catch (ValidationException e) {
            respond motorDeDatos.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'motorDeDatos.label', default: 'MotorDeDatos'), motorDeDatos.id])
                redirect motorDeDatos
            }
            '*' { respond motorDeDatos, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond motorDeDatosService.get(id)
    }

    def update(MotorDeDatos motorDeDatos) {
        if (motorDeDatos == null) {
            notFound()
            return
        }

        try {
            motorDeDatosService.save(motorDeDatos)
        } catch (ValidationException e) {
            respond motorDeDatos.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'motorDeDatos.label', default: 'MotorDeDatos'), motorDeDatos.id])
                redirect motorDeDatos
            }
            '*'{ respond motorDeDatos, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        motorDeDatosService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'motorDeDatos.label', default: 'MotorDeDatos'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'motorDeDatos.label', default: 'MotorDeDatos'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
