package fiuba.materia7510.aplicacion.autenticacion

import grails.test.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class AutenticadorServiceSpec extends Specification {

    AutenticadorService autenticadorService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Autenticador(...).save(flush: true, failOnError: true)
        //new Autenticador(...).save(flush: true, failOnError: true)
        //Autenticador autenticador = new Autenticador(...).save(flush: true, failOnError: true)
        //new Autenticador(...).save(flush: true, failOnError: true)
        //new Autenticador(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //autenticador.id
    }

    void "test get"() {
        setupData()

        expect:
        autenticadorService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Autenticador> autenticadorList = autenticadorService.list(max: 2, offset: 2)

        then:
        autenticadorList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        autenticadorService.count() == 5
    }

    void "test delete"() {
        Long autenticadorId = setupData()

        expect:
        autenticadorService.count() == 5

        when:
        autenticadorService.delete(autenticadorId)
        sessionFactory.currentSession.flush()

        then:
        autenticadorService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Autenticador autenticador = new Autenticador()
        autenticadorService.save(autenticador)

        then:
        autenticador.id != null
    }
}
