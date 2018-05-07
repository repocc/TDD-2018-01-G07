package dashboard

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class TableroController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Tablero.list(params), model:[tableroCount: Tablero.count()]
    }

    def show(Tablero tablero) {
        respond tablero
    }

    def create() {
        respond new Tablero(params)
    }

    @Transactional
    def save(Tablero tablero) {
        if (tablero == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tablero.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tablero.errors, view:'create'
            return
        }

        tablero.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tablero.label', default: 'Tablero'), tablero.id])
                redirect tablero
            }
            '*' { respond tablero, [status: CREATED] }
        }
    }

    def edit(Tablero tablero) {
        respond tablero
    }

    @Transactional
    def update(Tablero tablero) {
        if (tablero == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (tablero.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tablero.errors, view:'edit'
            return
        }

        tablero.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'tablero.label', default: 'Tablero'), tablero.id])
                redirect tablero
            }
            '*'{ respond tablero, [status: OK] }
        }
    }

    @Transactional
    def delete(Tablero tablero) {

        if (tablero == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        tablero.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'tablero.label', default: 'Tablero'), tablero.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    @Transactional
    	def agregarTablero(Tablero tablero) {
            if (tablero == null) {
                transactionStatus.setRollbackOnly()
                notFound()
                return
            }

           ExtrategiaVisualización EdVisualizacion;
           switch(params.TiposDeGraficos) {
           			case   "Derecha": EdVisualizacion = new ExtrategiaVisualizaciónDerecha(); break;
           			case "Izquierda": EdVisualizacion = new ExtrategiaVisualizaciónIzquierda(); break;
           			default:
           				tablero.errors.add "Tipo no valido"
           				respond tablero.errors, view:'edit'
           				return
           		}

           tablero.agregarInstrumento(new Instrumento (EdVisualizacion))

    		   tablero.save flush:true

           request.withFormat {
               form multipartForm {
                   flash.message = message(code: 'default.updated.message', args: [message(code: 'tablero.label', default: 'Tablero'), tablero.id])
                   redirect tablero
               }
               '*'{ respond tablero, [status: OK] }
           }
      }

      @Transactional
      	def RecibirDato(Tablero tablero) {
              if (tablero == null) {
                  transactionStatus.setRollbackOnly()
                  notFound()
                  return
              }

             tablero.datos.tomarDatos()
             tablero.texxt()
             tablero.datos.moke++

             for(Instrumento listener : tablero.datos.listeners) {
               listener.moke++
             }

      		  println(tablero.save(flush:true))
             tablero.datos.save(flush:true)

             request.withFormat {
                 form multipartForm {
                     flash.message = message(code: 'default.updated.message', args: [message(code: 'tablero.label', default: 'Tablero'), tablero.id])
                     redirect tablero
                 }
                 '*'{ respond tablero, [status: OK] }
             }
              println(tablero.datos.getErrors())
        }

      protected void notFound() {
          request.withFormat {
              form multipartForm {
                  flash.message = message(code: 'default.not.found.message', args: [message(code: 'tablero.label', default: 'Tablero'), params.id])
                  redirect action: "index", method: "GET"
              }
              '*'{ render status: NOT_FOUND }
          }
      }
}
