package dashboard

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MotorDeDatosController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond MotorDeDatos.list(params), model:[motorDeDatosCount: MotorDeDatos.count()]
    }

    def show(MotorDeDatos motorDeDatos) {
        respond motorDeDatos
    }

    def create() {
        respond new MotorDeDatos(params)
    }

    @Transactional
    def save(MotorDeDatos motorDeDatos) {
        if (motorDeDatos == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (motorDeDatos.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond motorDeDatos.errors, view:'create'
            return
        }

        motorDeDatos.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'motorDeDatos.label', default: 'MotorDeDatos'), motorDeDatos.id])
                redirect motorDeDatos
            }
            '*' { respond motorDeDatos, [status: CREATED] }
        }
    }

    def edit(MotorDeDatos motorDeDatos) {
        respond motorDeDatos
    }

    @Transactional
    def update(MotorDeDatos motorDeDatos) {
        if (motorDeDatos == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (motorDeDatos.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond motorDeDatos.errors, view:'edit'
            return
        }

        motorDeDatos.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'motorDeDatos.label', default: 'MotorDeDatos'), motorDeDatos.id])
                redirect motorDeDatos
            }
            '*'{ respond motorDeDatos, [status: OK] }
        }
    }

    @Transactional
    	def RecibirDato(MotorDeDatos motorDeDatos) {
          if (motorDeDatos == null) {
              transactionStatus.setRollbackOnly()
              notFound()
              return
          }

           motorDeDatos.tomarDatos()

    		   motorDeDatos.save (flush:true)

           request.withFormat {
               form multipartForm {
                   flash.message = message(code: "dato cargado: " + motorDeDatos.numero, args: [message(code: 'motorDeDatos.label', default: 'MotorDeDatos'), motorDeDatos.id])
                   redirect motorDeDatos
               }
               '*'{ respond motorDeDatos, [status: OK] }
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
