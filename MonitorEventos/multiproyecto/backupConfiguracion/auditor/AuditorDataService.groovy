package fiuba.materia7510.aplicacion.auditor


import grails.gorm.services.Service
import grails.gorm.services.Where
import groovy.transform.CompileStatic

import fiuba.materia7510.aplicacion.proveedor.Ticket


@CompileStatic
@Service(Auditor)
interface AuditDataService {

    Auditor save(String event, Long ticketId)

    Number count()

    List<Auditor> findAll(Map args)

    @Where({ ticketId == id })
    void deleteByTicketId(Long id)
}
