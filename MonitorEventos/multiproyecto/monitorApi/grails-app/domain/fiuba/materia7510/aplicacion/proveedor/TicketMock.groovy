package fiuba.materia7510.aplicacion.proveedor

import grails.rest.*

@Resource(uri='/ticketsmock')
class TicketMock extends Ticket {

	String caracteristica = "ESTE ES UN TICKET MOCK"
    
    static constraints = {
		importFrom Ticket
    }
}
