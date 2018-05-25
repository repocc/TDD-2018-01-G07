package tickets

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ProyectoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Proyecto.list(params), model:[proyectoCount: Proyecto.count()]
    }

    def show(Proyecto proyecto) {
        respond proyecto
    }

    def create() {
        respond new Proyecto(params)
    }

    @Transactional
    def save(Proyecto proyecto) {
        if (proyecto == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (proyecto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond proyecto.errors, view:'create'
            return
        }

        EstadoTicket fin1 = new EstadoTicket()
        fin1.nombre = "fin"
        EstadoTicket paso11 = new EstadoTicket()
        paso11.nombre = "paso1"
        paso11.siguientes.add(fin1)
        EstadoTicket inicio1 = new EstadoTicket()
        inicio1.nombre = "inicio"
        inicio1.siguientes.add(paso11)
        proyecto.siclos.add(inicio1)

        EstadoTicket fin2 = new EstadoTicket()
        fin2.nombre = "fin"
        EstadoTicket paso12 = new EstadoTicket()
        paso12.nombre = "paso2"
        paso12.siguientes.add(fin2)
        EstadoTicket paso22 = new EstadoTicket()
        paso22.nombre = "paso1"
        paso22.siguientes.add(paso12)
        EstadoTicket paso32 = new EstadoTicket()
        paso32.nombre = "paso1"
        paso32.siguientes.add(fin2)
        EstadoTicket inicio2 = new EstadoTicket()
        inicio2.nombre = "inicio"
        inicio2.siguientes.add(paso32)
        inicio2.siguientes.add(paso22)
        proyecto.siclos.add(inicio2)


        proyecto.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'proyecto.label', default: 'Proyecto'), proyecto.id])
                redirect proyecto
            }
            '*' { respond proyecto, [status: CREATED] }
        }
    }

    def edit(Proyecto proyecto) {
        respond proyecto
    }

    @Transactional
    def update(Proyecto proyecto) {
        if (proyecto == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (proyecto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond proyecto.errors, view:'edit'
            return
        }

        proyecto.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'proyecto.label', default: 'Proyecto'), proyecto.id])
                redirect proyecto
            }
            '*'{ respond proyecto, [status: OK] }
        }
    }

    @Transactional
    def agregarTicekt(Proyecto proyecto) {
        if (proyecto == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (proyecto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond proyecto.errors, view:'edit'
            return
        }

        Ticket nuevoTicket = new Ticket()
        nuevoTicket.nombre = params.nombre

        switch(params.tipoDeSiclo) {
             case   'Primer siclo': nuevoTicket.estadoCompleto = proyecto.siclos.first(); break;
             case  'Segundo siclo': nuevoTicket.estadoCompleto = proyecto.siclos.last() ; break;
             default:
               tablero.errors.add "Tipo no valido"
               respond tablero.errors, view:'edit'
               return
        }
        nuevoTicket.estado = nuevoTicket.estadoCompleto.nombre
        proyecto.lTickets.add(nuevoTicket)

        proyecto.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'proyecto.label', default: 'Proyecto'), proyecto.id])
                redirect proyecto
            }
            '*'{ respond proyecto, [status: OK] }
        }
    }


    @Transactional
    def delete(Proyecto proyecto) {

        if (proyecto == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        proyecto.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'proyecto.label', default: 'Proyecto'), proyecto.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'proyecto.label', default: 'Proyecto'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
