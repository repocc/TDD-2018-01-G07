package fiuba.materia7510.aplicacion.auditor

import grails.events.annotation.Subscriber
import grails.events.annotation.gorm.Listener
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEvent
import org.grails.datastore.mapping.engine.event.PostDeleteEvent
import org.grails.datastore.mapping.engine.event.PostInsertEvent
import org.grails.datastore.mapping.engine.event.PostUpdateEvent

import fiuba.materia7510.aplicacion.proveedor.Ticket

@Slf4j
@CompileStatic
class AuditorListenerService {

    AuditDataService auditDataService

    Long ticketId(AbstractPersistenceEvent event) {
        if ( event.entityObject instanceof Ticket ) {
            return ((Ticket) event.entityObject).id 
        }
        null
    }

    @Subscriber 
    void afterInsert(PostInsertEvent event) {
        Long ticketId = ticketId(event)
        if ( ticketId ) {
            log.info 'Antes de guardar ticket ...'
            auditDataService.save('Ticket guardado', ticketId)
        }
    }

}
