package dashboard

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BaseDeDatosController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond BaseDeDatos.list(params), model:[baseDeDatosCount: BaseDeDatos.count()]
    }

    def show(BaseDeDatos baseDeDatos) {
        respond baseDeDatos
    }

    def create() {
        respond new BaseDeDatos(params)
    }

    @Transactional
    def save(BaseDeDatos baseDeDatos) {
        if (baseDeDatos == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (baseDeDatos.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond baseDeDatos.errors, view:'create'
            return
        }

        baseDeDatos.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'baseDeDatos.label', default: 'BaseDeDatos'), baseDeDatos.id])
                redirect baseDeDatos
            }
            '*' { respond baseDeDatos, [status: CREATED] }
        }
    }

    def edit(BaseDeDatos baseDeDatos) {
        respond baseDeDatos
    }

    @Transactional
    def update(BaseDeDatos baseDeDatos) {
        if (baseDeDatos == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (baseDeDatos.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond baseDeDatos.errors, view:'edit'
            return
        }

        baseDeDatos.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'baseDeDatos.label', default: 'BaseDeDatos'), baseDeDatos.id])
                redirect baseDeDatos
            }
            '*'{ respond baseDeDatos, [status: OK] }
        }
    }

    @Transactional
    def RecibirDato(BaseDeDatos baseDeDatos) {
        if (baseDeDatos == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }


        baseDeDatos.texxt()
        baseDeDatos.mockGrails++
        baseDeDatos.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'baseDeDatos.label', default: 'BaseDeDatos'), baseDeDatos.id])
                redirect baseDeDatos
            }
            '*'{ respond baseDeDatos, [status: OK] }
        }
    }

    @Transactional
    def delete(BaseDeDatos baseDeDatos) {

        if (baseDeDatos == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        baseDeDatos.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'baseDeDatos.label', default: 'BaseDeDatos'), baseDeDatos.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'baseDeDatos.label', default: 'BaseDeDatos'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
