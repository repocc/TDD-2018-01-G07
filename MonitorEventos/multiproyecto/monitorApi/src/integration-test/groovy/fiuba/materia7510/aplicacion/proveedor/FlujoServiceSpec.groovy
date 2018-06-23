package fiuba.materia7510.aplicacion.proveedor

import grails.test.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class FlujoServiceSpec extends Specification {

    FlujoService flujoService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Flujo(...).save(flush: true, failOnError: true)
        //new Flujo(...).save(flush: true, failOnError: true)
        //Flujo flujo = new Flujo(...).save(flush: true, failOnError: true)
        //new Flujo(...).save(flush: true, failOnError: true)
        //new Flujo(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //flujo.id
    }

    void "test get"() {
        setupData()

        expect:
        flujoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Flujo> flujoList = flujoService.list(max: 2, offset: 2)

        then:
        flujoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        flujoService.count() == 5
    }

    void "test delete"() {
        Long flujoId = setupData()

        expect:
        flujoService.count() == 5

        when:
        flujoService.delete(flujoId)
        sessionFactory.currentSession.flush()

        then:
        flujoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Flujo flujo = new Flujo()
        flujoService.save(flujo)

        then:
        flujo.id != null
    }
}
