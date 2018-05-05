package dashboard

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ExtrategiaConteoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ExtrategiaConteo.list(params), model:[extrategiaConteoCount: ExtrategiaConteo.count()]
    }

    def show(ExtrategiaConteo extrategiaConteo) {
        respond extrategiaConteo
    }

    def create() {
        respond new ExtrategiaConteo(params)
    }

    @Transactional
    def save(ExtrategiaConteo extrategiaConteo) {
        if (extrategiaConteo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (extrategiaConteo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond extrategiaConteo.errors, view:'create'
            return
        }

        extrategiaConteo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'extrategiaConteo.label', default: 'ExtrategiaConteo'), extrategiaConteo.id])
                redirect extrategiaConteo
            }
            '*' { respond extrategiaConteo, [status: CREATED] }
        }
    }

    def edit(ExtrategiaConteo extrategiaConteo) {
        respond extrategiaConteo
    }

    @Transactional
    def update(ExtrategiaConteo extrategiaConteo) {
        if (extrategiaConteo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (extrategiaConteo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond extrategiaConteo.errors, view:'edit'
            return
        }

        extrategiaConteo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'extrategiaConteo.label', default: 'ExtrategiaConteo'), extrategiaConteo.id])
                redirect extrategiaConteo
            }
            '*'{ respond extrategiaConteo, [status: OK] }
        }
    }

    @Transactional
    def delete(ExtrategiaConteo extrategiaConteo) {

        if (extrategiaConteo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        extrategiaConteo.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'extrategiaConteo.label', default: 'ExtrategiaConteo'), extrategiaConteo.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'extrategiaConteo.label', default: 'ExtrategiaConteo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
