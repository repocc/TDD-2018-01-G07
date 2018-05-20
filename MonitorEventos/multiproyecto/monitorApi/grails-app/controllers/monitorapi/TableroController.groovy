package monitorapi

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class TableroController {

    TableroService tableroService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond tableroService.list(params), model:[tableroCount: tableroService.count()]
    }

    def show(Long id) {
        respond tableroService.get(id)
    }

    def create() {
        respond new Tablero(params)
    }

    def save(Tablero tablero) {
        if (tablero == null) {
            notFound()
            return
        }

        try {
            tableroService.save(tablero)
        } catch (ValidationException e) {
            respond tablero.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'tablero.label', default: 'Tablero'), tablero.id])
                redirect tablero
            }
            '*' { respond tablero, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond tableroService.get(id)
    }

    def update(Tablero tablero) {
        if (tablero == null) {
            notFound()
            return
        }

        try {
            tableroService.save(tablero)
        } catch (ValidationException e) {
            respond tablero.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'tablero.label', default: 'Tablero'), tablero.id])
                redirect tablero
            }
            '*'{ respond tablero, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        tableroService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'tablero.label', default: 'Tablero'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

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
              ExtrategiaConteo EdConteo;
              switch(params.TiposDeConteo) {
                   case     "SoloSpam": EdConteo = new ExtrategiaConteo('spam-count'); break;
                   case "TodoLosMails": EdConteo = new ExtrategiaConteo('email-count'); break;
                   default:
                     tablero.errors.add "Tipo no valido"
                     respond tablero.errors, view:'edit'
                     return
              }



               tablero.agregarInstrumento(new Instrumento (EdVisualizacion, EdConteo))

        		   tablero.save flush:true

               request.withFormat {
                   form multipartForm {
                       flash.message = message(code: 'default.updated.message', args: [message(code: 'tablero.label', default: 'Tablero'), tablero.id])
                       redirect tablero
                   }
                   '*'{ respond tablero, [status: OK] }
               }
          }

    def RecibirSpam (Tablero tablero){
      RecibirDato (tablero, '{"spam" true}')
    }

    def RecibirNoSpam (Tablero tablero){
      RecibirDato (tablero, '{"spam" false}')
    }

    def RecibirDato(Tablero tablero, String dato) {
                  if (tablero == null) {
                      transactionStatus.setRollbackOnly()
                      notFound()
                      return
                  }

                 tablero.datos.tomarDatos(dato)
                 tablero.datos.moke++

                 for(Instrumento listener : tablero.datos.listeners) {
                   listener.moke++
                 }

                 tablero.datos.save(flush:true)

                 request.withFormat {
                     form multipartForm {
                         flash.message = message(code: 'default.updated.message', args: [message(code: 'tablero.label', default: 'Tablero'), tablero.id])
                         redirect tablero
                     }
                     '*'{ respond tablero, [status: OK] }
                 }
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
