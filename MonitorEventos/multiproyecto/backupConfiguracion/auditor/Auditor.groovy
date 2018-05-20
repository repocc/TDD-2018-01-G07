package fiuba.materia7510.aplicacion.auditor

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Auditor {

    String event
    Long ticketId

    static constraints = {
        event 		nullable: false, blank: false
        ticketId 	nullable: false
    }
}
