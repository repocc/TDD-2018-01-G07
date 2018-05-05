package dashboard

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ExtrategiaVisualizaciónDerechaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ExtrategiaVisualizaciónDerecha.list(params), model:[extrategiaVisualizaciónDerechaCount: ExtrategiaVisualizaciónDerecha.count()]
    }

    def show(ExtrategiaVisualizaciónDerecha extrategiaVisualizaciónDerecha) {
        respond extrategiaVisualizaciónDerecha
    }

    def create() {
        respond new ExtrategiaVisualizaciónDerecha(params)
    }

    @Transactional
    def save(ExtrategiaVisualizaciónDerecha extrategiaVisualizaciónDerecha) {
        if (extrategiaVisualizaciónDerecha == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (extrategiaVisualizaciónDerecha.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond extrategiaVisualizaciónDerecha.errors, view:'create'
            return
        }

        extrategiaVisualizaciónDerecha.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'extrategiaVisualizaciónDerecha.label', default: 'ExtrategiaVisualizaciónDerecha'), extrategiaVisualizaciónDerecha.id])
                redirect extrategiaVisualizaciónDerecha
            }
            '*' { respond extrategiaVisualizaciónDerecha, [status: CREATED] }
        }
    }

    def edit(ExtrategiaVisualizaciónDerecha extrategiaVisualizaciónDerecha) {
        respond extrategiaVisualizaciónDerecha
    }

    @Transactional
    def update(ExtrategiaVisualizaciónDerecha extrategiaVisualizaciónDerecha) {
        if (extrategiaVisualizaciónDerecha == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (extrategiaVisualizaciónDerecha.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond extrategiaVisualizaciónDerecha.errors, view:'edit'
            return
        }

        extrategiaVisualizaciónDerecha.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'extrategiaVisualizaciónDerecha.label', default: 'ExtrategiaVisualizaciónDerecha'), extrategiaVisualizaciónDerecha.id])
                redirect extrategiaVisualizaciónDerecha
            }
            '*'{ respond extrategiaVisualizaciónDerecha, [status: OK] }
        }
    }

    @Transactional
    def delete(ExtrategiaVisualizaciónDerecha extrategiaVisualizaciónDerecha) {

        if (extrategiaVisualizaciónDerecha == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        extrategiaVisualizaciónDerecha.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'extrategiaVisualizaciónDerecha.label', default: 'ExtrategiaVisualizaciónDerecha'), extrategiaVisualizaciónDerecha.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'extrategiaVisualizaciónDerecha.label', default: 'ExtrategiaVisualizaciónDerecha'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
