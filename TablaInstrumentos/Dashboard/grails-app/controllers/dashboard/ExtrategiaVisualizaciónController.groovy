package dashboard

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ExtrategiaVisualizaciónController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ExtrategiaVisualización.list(params), model:[extrategiaVisualizaciónCount: ExtrategiaVisualización.count()]
    }

    def show(ExtrategiaVisualización extrategiaVisualización) {
        respond extrategiaVisualización
    }

    def create() {
        respond new ExtrategiaVisualización(params)
    }

    @Transactional
    def save(ExtrategiaVisualización extrategiaVisualización) {
        if (extrategiaVisualización == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (extrategiaVisualización.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond extrategiaVisualización.errors, view:'create'
            return
        }

        extrategiaVisualización.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'extrategiaVisualización.label', default: 'ExtrategiaVisualización'), extrategiaVisualización.id])
                redirect extrategiaVisualización
            }
            '*' { respond extrategiaVisualización, [status: CREATED] }
        }
    }

    def edit(ExtrategiaVisualización extrategiaVisualización) {
        respond extrategiaVisualización
    }

    @Transactional
    def update(ExtrategiaVisualización extrategiaVisualización) {
        if (extrategiaVisualización == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (extrategiaVisualización.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond extrategiaVisualización.errors, view:'edit'
            return
        }

        extrategiaVisualización.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'extrategiaVisualización.label', default: 'ExtrategiaVisualización'), extrategiaVisualización.id])
                redirect extrategiaVisualización
            }
            '*'{ respond extrategiaVisualización, [status: OK] }
        }
    }

    @Transactional
    def delete(ExtrategiaVisualización extrategiaVisualización) {

        if (extrategiaVisualización == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        extrategiaVisualización.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'extrategiaVisualización.label', default: 'ExtrategiaVisualización'), extrategiaVisualización.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'extrategiaVisualización.label', default: 'ExtrategiaVisualización'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
