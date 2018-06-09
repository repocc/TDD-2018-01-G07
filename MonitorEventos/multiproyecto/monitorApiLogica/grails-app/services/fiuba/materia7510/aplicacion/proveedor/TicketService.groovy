package fiuba.materia7510.aplicacion.proveedor

import grails.gorm.services.Service

@Service(Ticket)
interface TicketService {

    Ticket get(Serializable id)

    List<Ticket> list(Map args)

    Long count()

    void delete(Serializable id)

    Ticket save(Ticket ticket)

}
