package dashboard

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ExtrategiaVisualizaciónIzquierdaController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ExtrategiaVisualizaciónIzquierda.list(params), model:[extrategiaVisualizaciónIzquierdaCount: ExtrategiaVisualizaciónIzquierda.count()]
    }

    def show(ExtrategiaVisualizaciónIzquierda extrategiaVisualizaciónIzquierda) {
        respond extrategiaVisualizaciónIzquierda
    }

    def create() {
        respond new ExtrategiaVisualizaciónIzquierda(params)
    }

    @Transactional
    def save(ExtrategiaVisualizaciónIzquierda extrategiaVisualizaciónIzquierda) {
        if (extrategiaVisualizaciónIzquierda == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (extrategiaVisualizaciónIzquierda.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond extrategiaVisualizaciónIzquierda.errors, view:'create'
            return
        }

        extrategiaVisualizaciónIzquierda.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'extrategiaVisualizaciónIzquierda.label', default: 'ExtrategiaVisualizaciónIzquierda'), extrategiaVisualizaciónIzquierda.id])
                redirect extrategiaVisualizaciónIzquierda
            }
            '*' { respond extrategiaVisualizaciónIzquierda, [status: CREATED] }
        }
    }

    def edit(ExtrategiaVisualizaciónIzquierda extrategiaVisualizaciónIzquierda) {
        respond extrategiaVisualizaciónIzquierda
    }

    @Transactional
    def update(ExtrategiaVisualizaciónIzquierda extrategiaVisualizaciónIzquierda) {
        if (extrategiaVisualizaciónIzquierda == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (extrategiaVisualizaciónIzquierda.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond extrategiaVisualizaciónIzquierda.errors, view:'edit'
            return
        }

        extrategiaVisualizaciónIzquierda.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'extrategiaVisualizaciónIzquierda.label', default: 'ExtrategiaVisualizaciónIzquierda'), extrategiaVisualizaciónIzquierda.id])
                redirect extrategiaVisualizaciónIzquierda
            }
            '*'{ respond extrategiaVisualizaciónIzquierda, [status: OK] }
        }
    }

    @Transactional
    def delete(ExtrategiaVisualizaciónIzquierda extrategiaVisualizaciónIzquierda) {

        if (extrategiaVisualizaciónIzquierda == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        extrategiaVisualizaciónIzquierda.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'extrategiaVisualizaciónIzquierda.label', default: 'ExtrategiaVisualizaciónIzquierda'), extrategiaVisualizaciónIzquierda.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'extrategiaVisualizaciónIzquierda.label', default: 'ExtrategiaVisualizaciónIzquierda'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
