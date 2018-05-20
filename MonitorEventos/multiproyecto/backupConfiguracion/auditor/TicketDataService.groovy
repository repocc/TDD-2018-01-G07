package fiuba.materia7510.aplicacion.auditor

import grails.gorm.services.Service
import groovy.transform.CompileStatic
import fiuba.materia7510.aplicacion.proveedor.Ticket

@CompileStatic
@Service(Ticket)
interface TicketDataService {

    Ticket save(Ticket ticket)
	/*estado:est , flujo:f, codigo:cod, titulo: tit, descripcion: desc, propietario: prop*/
    List<Ticket> findAll()

    Ticket update(Serializable id, String titulo )

    void delete(Serializable id)
}
